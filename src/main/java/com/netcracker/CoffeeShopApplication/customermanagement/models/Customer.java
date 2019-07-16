package com.netcracker.CoffeeShopApplication.customermanagement.models;

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
@ApiModel(value = "Customer", description = "Customer model")
@Getter
@Setter
@EqualsAndHashCode
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
    @EqualsAndHashCode.Exclude
    String address;


}
