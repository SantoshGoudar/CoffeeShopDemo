package com.netcracker.CoffeeShopApplication.ordermanagement.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Item")
public class Item {
    @ApiModelProperty(name = "Name",required = true)
    String name;
    @ApiModelProperty(name = "Price",required = true)
    float price;
    @ApiModelProperty(name = "Quantity",required = true)
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
