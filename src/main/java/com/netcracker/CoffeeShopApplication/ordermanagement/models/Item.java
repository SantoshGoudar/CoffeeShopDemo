package com.netcracker.CoffeeShopApplication.ordermanagement.models;

public class Item {
    String name;
    float price;
    int qty;

    public Item(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public Item() {
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
