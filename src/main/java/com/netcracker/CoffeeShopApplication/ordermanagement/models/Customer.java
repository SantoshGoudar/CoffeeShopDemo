package com.netcracker.CoffeeShopApplication.ordermanagement.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Document
@ApiModel(value = "OrderCustomer")
@Getter
@Setter
@EqualsAndHashCode
public class Customer {
    @ApiModelProperty(name = "Name")
    String name;
    @ApiModelProperty(name = "Email")
    String email;
    @Id
    @NotNull(message="{customer.phone.notnull}")
    @NotEmpty(message = "{customer.phone.notempty}")
    @Size(min = 10, max = 10,message = "{customer.phone.size}")
    @ApiModelProperty(name = "Phone", required = true)
    String phone;
    @EqualsAndHashCode.Exclude
    String address;

    public Customer() {
    }

    public Customer(@NotNull @NotEmpty String name, @Email @NotNull @NotEmpty String email, @NotNull @NotEmpty @Size(min = 10, max = 10) String phone, String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }


}

