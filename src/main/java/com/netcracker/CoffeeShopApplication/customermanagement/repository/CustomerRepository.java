package com.netcracker.CoffeeShopApplication.customermanagement.repository;

import com.netcracker.CoffeeShopApplication.customermanagement.models.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends
        MongoRepository<Customer, String> {
}
