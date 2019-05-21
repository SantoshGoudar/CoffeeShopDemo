package com.netcracker.CoffeeShopApplication.authenticationservice.controllers;

import com.netcracker.CoffeeShopApplication.authenticationservice.model.User;
import com.netcracker.CoffeeShopApplication.authenticationservice.repository.UserRepository;
import com.netcracker.CoffeeShopApplication.authenticationservice.service.AuthenicationService;
import com.netcracker.CoffeeShopApplication.constants.StringConstants;
import com.netcracker.CoffeeShopApplication.exceptions.CustomException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping(StringConstants.AUTH)
@Api(value = "Authentication API", description = "Authentication of the USER is handled here")
@Slf4j
public class AuthenticationController {

    @Autowired
    AuthenicationService authenicationService;


    @PostMapping(StringConstants.LOGIN)
    @ApiOperation("Authenticates the User and the sets the JWT auth header, that header should be used in subsequent requests for authorization")
    public void authenticate(@RequestBody User user, HttpServletResponse response) throws CustomException {
        log.info("Authentication called");
        authenicationService.authenticateUser(user, response);
    }

}
