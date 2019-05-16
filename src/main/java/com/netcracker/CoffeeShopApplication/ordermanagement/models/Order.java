package com.netcracker.CoffeeShopApplication.ordermanagement.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Document
public class Order {
    @Id
    String orderId;
    @NotEmpty
    List<Item> items;
    @NotNull @Valid
    Customer customer;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Indexed(expireAfterSeconds = 20)
    Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderNo(String orderId) {
        this.orderId = orderId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
