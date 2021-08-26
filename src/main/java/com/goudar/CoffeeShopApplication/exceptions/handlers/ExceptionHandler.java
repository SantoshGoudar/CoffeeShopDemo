package com.goudar.CoffeeShopApplication.exceptions.handlers;

import com.goudar.CoffeeShopApplication.exceptions.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({CustomException.class, ParseException.class})
    public ResponseEntity handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler({MethodArgumentNotValidException.class})
    public List<String> handleValidationExceptions(MethodArgumentNotValidException ex) {

        return ex.getBindingResult()
                .getFieldErrors().stream()
                .map(objectError -> {
                    return objectError.getObjectName() + "." + objectError.getField() + " : " + objectError.getDefaultMessage();
                })
                .collect(Collectors.toList());
    }
}
