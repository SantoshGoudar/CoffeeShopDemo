package com.goudar.CoffeeShopApplication.exceptions;

public class CustomException extends Exception {
    public CustomException() {
    }

    public CustomException(String s) {
        super(s);
    }

    public CustomException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
