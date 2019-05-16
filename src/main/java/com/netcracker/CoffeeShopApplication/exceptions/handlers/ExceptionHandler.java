package com.netcracker.CoffeeShopApplication.exceptions.handlers;

import com.netcracker.CoffeeShopApplication.exceptions.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.text.ParseException;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({CustomException.class, ParseException.class})
    public ResponseEntity handleException(Exception e ){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

}
