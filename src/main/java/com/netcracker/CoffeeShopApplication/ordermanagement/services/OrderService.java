package com.netcracker.CoffeeShopApplication.ordermanagement.services;

import com.netcracker.CoffeeShopApplication.exceptions.CustomException;
import com.netcracker.CoffeeShopApplication.ordermanagement.models.Order;


import java.util.Date;
import java.util.List;

public interface OrderService {
    public List<Order> listAll();

    public Order findById(String orderId) throws CustomException;

    public Order save(Order order);

    public Order update(Order order);

    /**
     * @param date: string date of format yyyy-mm-dd
     */
    public List<Order> listOrdersPerDate(String date);

    /**
     * @param weekN: String calender week from starting of the year
     */
    public List<Order> listOrdersPerWeek(int weekN);
    /**
     * @param date: string date of format yyyy
     */
    public List<Order> listOrdersPerYear(String date);
    /**
     * @param month: String month number - Jan starts with 0
     */
    public List<Order> listOrdersPerMonth(int month);
    /**
     * @param date: string date of format yyyy
     */
    public List<Order> deleteOrdersPerYear(String date);


    public void deleteById(String orderId);

    public void delete(Order order);

    public void sendBillToCustomer(Order order);
}
