package com.netcracker.CoffeeShopApplication.productmanagement.models;

import com.netcracker.CoffeeShopApplication.productmanagement.validators.annotations.Enum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @NotEmpty(message = "{product.name.notempty}")
    @NotNull(message = "{product.name.notnull}")
    String name;
    @NotNull(message = "{product.category.notnull}")
    Category category;
    float price;

}
