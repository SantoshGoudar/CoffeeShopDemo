package com.netcracker.CoffeeShopApplication.productmanagement.models;

public enum Category {
    COLD_COFFEE("Cold Coffee"),
    HOT_COFFEE("Hot Coffe"),
    SNACK("Snack");
    String desc;

    Category(String desc) {
        this.desc = desc;
    }

}
