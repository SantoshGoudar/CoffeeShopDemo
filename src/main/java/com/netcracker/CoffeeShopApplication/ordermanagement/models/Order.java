package com.netcracker.CoffeeShopApplication.ordermanagement.models;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class Order {
    @Id
    @ApiModelProperty(hidden = true)
    String orderId;
    @NotEmpty(message = "{order.items.notempty}")
    @ApiModelProperty(required = true, name = "Items")
    List<Item> items;
    @NotNull(message = "{order.customer.notnull}") @Valid
    @ApiModelProperty(required = true, name = "Customer")
    Customer customer;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Indexed(expireAfterSeconds = 3600)
    @ApiModelProperty(hidden = true)
    Date date;


}
