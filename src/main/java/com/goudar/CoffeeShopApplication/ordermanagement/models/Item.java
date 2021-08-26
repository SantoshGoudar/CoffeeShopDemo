package com.goudar.CoffeeShopApplication.ordermanagement.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel("Item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @ApiModelProperty(name = "Name", required = true)
    String name;
    @ApiModelProperty(name = "Price", required = true)
    float price;
    @ApiModelProperty(name = "Quantity", required = true)
    int qty;


}
