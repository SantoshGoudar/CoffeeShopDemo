package com.goudar.CoffeeShopApplication.constants;

public interface StringConstants {

    String PATH_SEPERATOR = "/";
    String AUTH = "/auth";
    String LOGIN = "/login";
    String CUSTOMERS = "/customers";
    String CUSTOMER_ID = "/{id}";
    String ORDERS = "/orders";
    String ORDERS_WITH_ID = ORDERS + "/{orderId}";
    String REPORT_ORDERS = ORDERS + "/reports";
    String INVOICE = "/invoice/{orderId}";
    String PRODUCTS = "/products";
    String PRODUCTS_WITH_NAME = PRODUCTS + "/{name}";
}
