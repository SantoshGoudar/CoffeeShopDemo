package com.goudar.CoffeeShopApplication.productmanagement.repository;

import com.goudar.CoffeeShopApplication.productmanagement.models.Product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product,String> {

}
