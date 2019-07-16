package com.netcracker.CoffeeShopApplication.ordermanagement.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class DbSequence {
    @Id
    String name;
    long seq;


}
