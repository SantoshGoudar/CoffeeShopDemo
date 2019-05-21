package com.netcracker.CoffeeShopApplication.constants;

public class StringConstants {
    public static final String PATH_SEPERATOR = "/";
    public static final String AUTH = "/auth";
    public static final String LOGIN = "/login";
    public static final String CUSTOMERS = "/customers";
    public static final String CUSTOMER_ID = "/{id}";
    public static final String ORDERS = "/orders";
    public static final String ORDERS_WITH_ID = ORDERS + "/{orderId}";
    public static final String REPORT_ORDERS = "/reports" + ORDERS;
    public static final String INVOICE = "/invoice/{orderId}";
    public static final String PRODUCTS = "/products";
    public static final String PRODUCTS_WITH_NAME = PRODUCTS + "/{name}";
}
