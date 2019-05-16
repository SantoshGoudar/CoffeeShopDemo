package com.netcracker.CoffeeShopApplication.authenticationservice.repository;

import com.netcracker.CoffeeShopApplication.authenticationservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
}
