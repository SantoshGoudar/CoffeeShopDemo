package com.goudar.CoffeeShopApplication.customermanagement.repository;

import com.goudar.CoffeeShopApplication.customermanagement.models.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends
        MongoRepository<Customer, String> {
}
