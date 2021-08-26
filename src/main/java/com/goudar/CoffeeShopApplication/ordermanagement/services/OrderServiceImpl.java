package com.goudar.CoffeeShopApplication.ordermanagement.services;

import com.goudar.CoffeeShopApplication.exceptions.CustomException;
import com.goudar.CoffeeShopApplication.ordermanagement.models.Customer;
import com.goudar.CoffeeShopApplication.ordermanagement.models.Order;
import com.goudar.CoffeeShopApplication.ordermanagement.repositoy.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.*;


import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository repository;
    @Autowired
    SequenceService sequenceService;
    private String seq_key = "OrderSequence";
    @Autowired
    MongoOperations mongoOp;
    @Autowired
    RestServiceCaller restServiceCaller;
    @Autowired
    Environment environment;


    @Override
    public List<Order> listAll() {
        return repository.findAll();
    }

    @Override
    public Order findById(String orderId) throws CustomException {
        return repository.findById(orderId).orElseThrow(() -> new CustomException("No Order present with orderId " + orderId));

    }

    @Override
    public Order save(Order order) {
        if (order.getOrderId() == null) {
            long nextSequenceNo = sequenceService.getNextSequenceNo(seq_key);
            order.setOrderId("CS" + nextSequenceNo);
        }
        if (order.getDate() == null) {
            Calendar calendar = Calendar.getInstance();
            Date now = calendar.getTime();
            order.setDate(now);
        }
        return repository.save(order);
    }

    @Override
    public Order save(Order order, String header) throws CustomException{
        String endPoint = "http://" + environment.getProperty("server.host") + ":" + environment.getProperty("server.port") + environment.getProperty("customerEndPoint");
        if (order.getOrderId() == null) {
            long nextSequenceNo = sequenceService.getNextSequenceNo(seq_key);
            order.setOrderId("CS" + nextSequenceNo);
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", header);
        Customer customerEndPoint = restServiceCaller.getCustomerObject(endPoint + "/" + order.getCustomer().getPhone(), headers);
        if (customerEndPoint == null) {
            restServiceCaller.putCustomerObject(endPoint, headers, order.getCustomer());
        } else {
            order.setCustomer(customerEndPoint);
        }
        if (order.getDate() == null) {
            Calendar calendar = Calendar.getInstance();
            Date now = calendar.getTime();
            order.setDate(now);
        }
        return repository.save(order);
    }

    @Override
    public List<String> save(List<Order> orders) {
        List<Order> list = repository.saveAll(orders);
        return list.stream().map(Order::getOrderId).collect(Collectors.toList());
    }

    @Override
    public Order update(Order order) {
        if (order.getOrderId() == null) {
            long nextSequenceNo = sequenceService.getNextSequenceNo(seq_key);
            order.setOrderId("CS" + nextSequenceNo);
        }
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        order.setDate(now);
        return repository.save(order);
    }

    @Override
    public List<Order> listOrdersPerDate(String date) {

        ProjectionOperation projection = Aggregation.project("orderId", "items", "customer", "date").and(DateOperators.dateOf("date").toString("%Y-%m-%d")).as("dmy");
        MatchOperation match = Aggregation.match(Criteria.where("dmy").is(date));
        Aggregation aggregation = Aggregation.newAggregation(projection, match);
        AggregationResults<Order> aggregate = mongoOp.aggregate(aggregation, Order.class, Order.class);
        return aggregate.getMappedResults();
    }

    @Override
    public List<Order> listOrdersPerMonth(int month) {

        ProjectionOperation projection = Aggregation.project("orderId", "items", "customer", "date").and(DateOperators.dateOf("date").toString("%m")).as("dmy");
        MatchOperation match = Aggregation.match(Criteria.where("dmy").is(month < 10 ? "0" + month : month));
        Aggregation aggregation = Aggregation.newAggregation(projection, match);
        AggregationResults<Order> aggregate = mongoOp.aggregate(aggregation, Order.class, Order.class);
        return aggregate.getMappedResults();
    }

    @Override
    public List<Order> listOrdersPerWeek(int weekN) {
        ProjectionOperation projection = Aggregation.project("orderId", "items", "customer", "date").and(DateOperators.dateOf("date").isoWeek()).as("dmy");
        MatchOperation match = Aggregation.match(Criteria.where("dmy").is(weekN < 10 ? "0" + weekN : weekN));
        Aggregation aggregation = Aggregation.newAggregation(projection, match);
        AggregationResults<Order> aggregate = mongoOp.aggregate(aggregation, Order.class, Order.class);
        return aggregate.getMappedResults();
    }

    @Override
    public List<Order> listOrdersPerYear(String date) {
        ProjectionOperation projection = Aggregation.project("orderId", "items", "customer", "date").and(DateOperators.dateOf("date").toString("%Y")).as("dmy");
        MatchOperation match = Aggregation.match(Criteria.where("dmy").is(date));
        Aggregation aggregation = Aggregation.newAggregation(projection, match);
        AggregationResults<Order> aggregate = mongoOp.aggregate(aggregation, Order.class, Order.class);
        return aggregate.getMappedResults();
    }

    @Override
    public List<Order> deleteOrdersPerYear(String date) {
        ProjectionOperation projection = Aggregation.project("orderId", "items", "customer", "date").and(DateOperators.dateOf("date").toString("%Y-%m-%d")).as("dmy");
        MatchOperation match = Aggregation.match(Criteria.where("dmy").is(date));
        Aggregation aggregation = Aggregation.newAggregation(projection, match);
        AggregationResults<Order> aggregate = mongoOp.aggregate(aggregation, Order.class, Order.class);

        return aggregate.getMappedResults();
    }

    @Override
    public void delete(Order order) {
        repository.delete(order);
    }

    @Override
    public void deleteById(String orderId) {
        repository.deleteById(orderId);
    }

    @Override
    public void sendBillToCustomer(Order order) {

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(order.getCustomer().getEmail());
        email.setSubject("Coffee Shop Bill " + order.getOrderId());
        email.setText("order");
        // mailSender.send(email);
    }
}
