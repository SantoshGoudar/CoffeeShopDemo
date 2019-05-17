package com.netcracker.CoffeeShopApplication.ordermanagement.controllers;

import com.netcracker.CoffeeShopApplication.exceptions.CustomException;
import com.netcracker.CoffeeShopApplication.ordermanagement.models.Order;
import com.netcracker.CoffeeShopApplication.ordermanagement.services.IReportGenerator;
import com.netcracker.CoffeeShopApplication.ordermanagement.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/coffeeshop")
public class OrderController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    OrderService orderService;

    @Autowired
    IReportGenerator reportGenerator;
    @Autowired
    EmailService emailService;

    @GetMapping("/orders")
    public List<Order> listAllOrders(@RequestParam(required = false) String date, @RequestParam(required = false) String week, @RequestParam(required = false) String month, @RequestParam(required = false) String year) throws ParseException {
        logger.info("list all orders called");
        if (date != null) {
            logger.info("list all orders per date " + date);
            return orderService.listOrdersPerDate(date);
        }
        if (week != null) {
            logger.info("list all orders per week " + week);
            return orderService.listOrdersPerWeek(Integer.parseInt(week));
        }
        if (month != null) {
            logger.info("list all orders per month " + month);
            return orderService.listOrdersPerMonth(Integer.parseInt(month));
        }
        if (year != null) {
            logger.info("list all orders per year " + year);
            return orderService.listOrdersPerYear(year);
        }
        return orderService.listAll();
    }

    @GetMapping("/orders/{orderId}")
    public Order getOrder(@PathVariable String orderId) throws CustomException {
        logger.info("get order by orderID  " + orderId);
        Order byId = orderService.findById(orderId);

        return byId;
    }

    @PostMapping("/orders")
    public Order updateOrder(@RequestBody @Valid Order order, HttpServletRequest request) {
        logger.info("update order " + order.getOrderId());
        String authorization = request.getHeader("Authorization");
        return orderService.save(order, authorization);
    }

    @PutMapping("/orders")
    public Order addOrder(@RequestBody @Valid Order order, HttpServletRequest request) {
        logger.info("Add new Order ");
        String authorization = request.getHeader("Authorization");
        return orderService.save(order, authorization);
    }

    @DeleteMapping("/orders/{orderId}")
    public String deleteOrder(@PathVariable String orderId) {
        logger.info("Delete Order " + orderId);
        orderService.deleteById(orderId);
        return "Deleted Successfully";
    }

    @GetMapping("/report/orders")
    public void getReport(HttpServletResponse response, @RequestParam(required = false) String date, @RequestParam(required = false) String week, @RequestParam(required = false) String month, @RequestParam(required = false) String year) throws ParseException, IOException, CustomException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; file=sales_report.csv");
        logger.info("report generation called ");
        List<Order> orders;

        if (date != null) {
            orders = orderService.listOrdersPerDate(date);
            logger.info("report generation called for date " + date);
            reportGenerator.writeDailyReport(response.getWriter(), orders);
        }
        if (week != null) {
            orders = orderService.listOrdersPerWeek(Integer.parseInt(week));
            logger.info("report generation called for week " + week);
            reportGenerator.writeWeeklyReport(response.getWriter(), orders);
        }
        if (month != null) {
            logger.info("report generation called for month " + month);
            orders = orderService.listOrdersPerMonth(Integer.parseInt(month));
            reportGenerator.writeMonthlyReport(response.getWriter(), orders);
        }
        if (year != null) {
            logger.info("report generation called for year " + year);
            orders = orderService.listOrdersPerYear(year);
        }


    }

    @GetMapping("invoice/{orderId}")
    public void sendInVoiceViaEmail(@PathVariable String orderId) throws CustomException {
        Order byId = orderService.findById(orderId);
        emailService.sendSimpleMessage(byId);
    }

}
