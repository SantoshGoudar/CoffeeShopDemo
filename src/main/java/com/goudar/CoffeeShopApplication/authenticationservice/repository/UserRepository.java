package com.goudar.CoffeeShopApplication.authenticationservice.repository;

import com.goudar.CoffeeShopApplication.authenticationservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
}
