package com.goudar.CoffeeShopApplication.ordermanagement.repositoy;

import com.goudar.CoffeeShopApplication.ordermanagement.models.Order;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    @Query("{date:{$gte: ?0,$lte:?0}}")
    public List<Order> findByDate(Date date);
}
