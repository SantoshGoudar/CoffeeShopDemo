package com.netcracker.CoffeeShopApplication.customermanagement.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Document
@ApiModel(value = "Customer", description = "Customer model")
public class Customer {

    @NotNull
    @NotEmpty
    @ApiModelProperty(value = "Name", required = true)
    String name;
    @Email @NotNull @NotEmpty
    @ApiModelProperty(value = "Email ID", required = true)
    String email;
    @Id
    @NotNull
    @NotEmpty
    @Size(min = 10, max = 10)
    @ApiModelProperty(value = "Phone number", required = true)
    String phone;
    @ApiModelProperty(value = "Address")
    String address;

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
