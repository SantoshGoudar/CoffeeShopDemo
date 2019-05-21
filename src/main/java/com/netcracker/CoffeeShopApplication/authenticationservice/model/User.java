package com.netcracker.CoffeeShopApplication.authenticationservice.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document
@ApiModel(description = "User - ")
@Getter
@Setter
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
