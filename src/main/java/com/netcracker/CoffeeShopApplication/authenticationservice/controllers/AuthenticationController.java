package com.netcracker.CoffeeShopApplication.authenticationservice.controllers;

import com.netcracker.CoffeeShopApplication.authenticationservice.model.User;
import com.netcracker.CoffeeShopApplication.authenticationservice.repository.UserRepository;
import com.netcracker.CoffeeShopApplication.exceptions.CustomException;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.cert.CertStoreException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/coffeeshop")
public class AuthenticationController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthenticationTokenProvider authenticationTokenProvider;

    @Autowired
    UserRepository users;

    @PostMapping("/login")
    @ApiOperation("Authenticates the User and the sets the JWT auth header")
    public void authenticate(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) throws CustomException {
        logger.info("Authenticating User ");
        try {
            String username = user.getUserName();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, user.getPassword()));
            User dbUser = this.users.findById(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found"));
            List<String> roles = new ArrayList<>();
            roles.add("ROLE_" + dbUser.getRole());
            String token = authenticationTokenProvider.createToken(username, roles);
            response.addHeader("Authorization", "Bearer " + token);
        } catch (AuthenticationException e) {
            logger.error("authentication failed due to " + e.getMessage(), e);
            throw new CustomException("Invalid username/password supplied");
        }
    }

}
