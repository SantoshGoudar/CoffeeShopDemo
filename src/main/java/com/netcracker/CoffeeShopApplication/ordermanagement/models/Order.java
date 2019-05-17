package com.netcracker.CoffeeShopApplication.ordermanagement.models;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("Order")
public class Order {
    @Id
    @ApiModelProperty(hidden = true)
    String orderId;
    @NotEmpty
    @ApiModelProperty(required = true, name = "Items")
    List<Item> items;
    @NotNull @Valid
    @ApiModelProperty(required = true, name = "Customer")
    Customer customer;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Indexed(expireAfterSeconds = 20)
    @ApiModelProperty(hidden = true)
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
