package com.netcracker.CoffeeShopApplication.authenticationservice.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document
@ApiModel(description = "User ")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @NotNull
    String userName;
    @NotNull
    String password;
    @ApiModelProperty(hidden = true)
    String role;
}
