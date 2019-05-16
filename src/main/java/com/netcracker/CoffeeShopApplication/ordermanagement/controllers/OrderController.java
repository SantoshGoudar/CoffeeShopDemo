package com.netcracker.CoffeeShopApplication.ordermanagement.controllers;

import com.netcracker.CoffeeShopApplication.exceptions.CustomException;
import com.netcracker.CoffeeShopApplication.ordermanagement.models.Order;
import com.netcracker.CoffeeShopApplication.ordermanagement.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/coffeeshop")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/orders")
    public List<Order> listAllOrders(@RequestParam(required = false) String date, @RequestParam(required = false) String week, @RequestParam(required = false) String month, @RequestParam(required = false) String year) throws ParseException {
        if (date != null) {
            return orderService.listOrdersPerDate(date);
        }
        if (week != null) {
            return orderService.listOrdersPerWeek(Integer.parseInt(week));
        }
        if (month != null) {
            return orderService.listOrdersPerMonth(Integer.parseInt(month));
        }
        if (year != null) {
            return orderService.listOrdersPerYear(year);
        }
        return orderService.listAll();
    }

    @GetMapping("/orders/{orderId}")
    public Order getOrder(@PathVariable String orderId) throws CustomException {

        Order byId = orderService.findById(orderId);

        return byId;
    }

    @PostMapping("/orders")
    public Order updateOrder(@RequestBody @Valid Order order) {
        return
                orderService.save(order);
    }

    @PutMapping("/orders")
    public Order addOrder(@RequestBody @Valid Order order) {
        return orderService.save(order);
    }

    @DeleteMapping("/orders/{orderId}")
    public String deleteOrder(@PathVariable String orderId) {
        orderService.deleteById(orderId);
        return "Deleted Successfully";
    }


}
