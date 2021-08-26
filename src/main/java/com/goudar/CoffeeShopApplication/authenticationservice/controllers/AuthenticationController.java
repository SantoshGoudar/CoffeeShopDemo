package com.goudar.CoffeeShopApplication.authenticationservice.controllers;

import com.goudar.CoffeeShopApplication.constants.StringConstants;
import com.goudar.CoffeeShopApplication.authenticationservice.model.User;
import com.goudar.CoffeeShopApplication.authenticationservice.service.AuthenicationService;
import com.goudar.CoffeeShopApplication.authenticationservice.service.UserService;
import com.goudar.CoffeeShopApplication.exceptions.CustomException;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(StringConstants.AUTH)
@Api(value = "Authentication API", description = "Authentication of the USER is handled here")
@Slf4j
public class AuthenticationController {

    @Autowired
    AuthenicationService authenicationService;

    @Autowired
    UserService userService;

    @PostMapping(StringConstants.LOGIN)
    @ApiOperation(value = "Authenticates the User and the sets the JWT auth header, that header should be used in subsequent requests for authorization", tags = {"Authentication"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Authentication successfull", responseHeaders = {@ResponseHeader(name = "Authentication", description = "Bearer Token")}),
    })
    public void authenticate(@RequestBody User user, HttpServletResponse response) throws CustomException {
        log.info("Authentication called");
        authenicationService.authenticateUser(user, response);
    }
    @PostMapping("/addUser")
    @ApiOperation(value = "Saves User",tags = {"Authentication"})
    public void sav(@RequestBody User user) throws CustomException {
        log.info("Authentication called");
        userService.save(user);
    }
}
