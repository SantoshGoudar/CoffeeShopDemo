package com.netcracker.CoffeeShopApplication.productmanagement.repository;

import com.netcracker.CoffeeShopApplication.productmanagement.models.Product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product,String> {

}
