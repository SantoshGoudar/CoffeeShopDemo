package com.netcracker.CoffeeShopApplication.ordermanagement.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Document
@ApiModel(value = "OrderCustomer")
public class Customer {
    @ApiModelProperty(name = "Name")
    String name;
    @ApiModelProperty(name = "Email")
    String email;
    @Id
    @NotNull
    @NotEmpty
    @Size(min = 10, max = 10)
    @ApiModelProperty(name = "Phone", required = true)
    String phone;
    String address;

    public Customer() {
    }

    public Customer(@NotNull @NotEmpty String name, @Email @NotNull @NotEmpty String email, @NotNull @NotEmpty @Size(min = 10, max = 10) String phone, String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

