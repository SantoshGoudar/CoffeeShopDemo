package com.netcracker.CoffeeShopApplication.productmanagement.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Document
public class Product {
    @Id
    @NotEmpty
    @NotNull
    String name;
    @NotNull
    Category category;
    float price;

    public Product(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public Product() {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
