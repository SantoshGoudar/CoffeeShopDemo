package com.goudar.CoffeeShopApplication.authenticationservice.service;

import com.goudar.CoffeeShopApplication.authenticationservice.controllers.AuthenticationTokenProvider;
import com.goudar.CoffeeShopApplication.authenticationservice.model.User;
import com.goudar.CoffeeShopApplication.authenticationservice.repository.UserRepository;
import com.goudar.CoffeeShopApplication.exceptions.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AuthenicationService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthenticationTokenProvider authenticationTokenProvider;

    @Autowired
    UserRepository users;

    public void authenticateUser(User user, HttpServletResponse response) throws CustomException {
        try {

            String username = user.getUserName();
            log.info("authenticating user " + user);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, user.getPassword()));
            User dbUser = this.users.findById(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found"));
            List<String> roles = new ArrayList<>();
            roles.add("ROLE_" + dbUser.getRole());
            String token = authenticationTokenProvider.createToken(username, roles);
            response.addHeader("Authorization", "Bearer " + token);
        } catch (AuthenticationException e) {
            log.error("authentication failed due to " + e.getMessage(), e);
            throw new CustomException("Invalid username/password supplied");
        }
    }
}
