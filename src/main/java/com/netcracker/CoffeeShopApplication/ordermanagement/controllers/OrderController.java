package com.netcracker.CoffeeShopApplication.ordermanagement.controllers;


import com.netcracker.CoffeeShopApplication.exceptions.CustomException;
import com.netcracker.CoffeeShopApplication.ordermanagement.models.Customer;
import com.netcracker.CoffeeShopApplication.ordermanagement.models.Order;
import com.netcracker.CoffeeShopApplication.ordermanagement.services.IReportGenerator;
import com.netcracker.CoffeeShopApplication.ordermanagement.services.OrderService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/coffeeshop")
@Api(value = "Order Management API", description = "All Order Related API along with Support for Sales Report, Invoice Generation, Email service")
public class OrderController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    OrderService orderService;

    @Autowired
    IReportGenerator reportGenerator;
    @Autowired
    EmailService emailService;

    @GetMapping("/orders")
    @ApiOperation(value = "List All Orders According to the date,week,month or year", response = com.netcracker.CoffeeShopApplication.ordermanagement.models.Customer.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Listed Orders Successfully", response = Customer.class),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Could not get Orders"),
            @ApiResponse(code = 403, message = "User not Authorized")
    })
    public List<Order> listAllOrders(@RequestParam(required = false) @ApiParam(value = "Date : used to query Orders per DATE", name = "Date") String date, @RequestParam(required = false) @ApiParam(name = "Week", value = "Week : Calendar week Number per year : to get Orders per week") String week, @RequestParam(required = false) @ApiParam(name = "Month", value = "Month no : for getting Orders per month") String month, @RequestParam(required = false) @ApiParam(name = "Year", value = "Year : to get the Orders per year") String year) throws ParseException {
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
    @ApiOperation(value = "Get a Order with Order Number specified", response = Customer.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Got the Order with Order number", response = Customer.class),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Could not get Order"),
            @ApiResponse(code = 403, message = "User not Authorized")
    })
    public Order getOrder(@PathVariable String orderId) throws CustomException {
        logger.info("get order by orderID  " + orderId);
        Order byId = orderService.findById(orderId);

        return byId;
    }

    @PostMapping("/orders")
    @ApiOperation(value = "Update existing Order in the CoffeeShop", response = Customer.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Order Added Successfully", response = Customer.class),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Could not add Order"),
            @ApiResponse(code = 403, message = "User not Authorized")
    })
    public Order updateOrder(@RequestBody @Valid Order order, HttpServletRequest request) {
        logger.info("update order " + order.getOrderId());
        String authorization = request.getHeader("Authorization");
        return orderService.save(order, authorization);
    }

    @PutMapping("/orders")
    @ApiOperation(value = "Add new Order to CoffeeShop", response = Customer.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Order Added Successfully", response = Customer.class),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Could not add Order"),
            @ApiResponse(code = 403, message = "User not Authorized")
    })
    public Order addOrder(@RequestBody @Valid Order order, HttpServletRequest request) {
        logger.info("Add new Order ");
        String authorization = request.getHeader("Authorization");
        return orderService.save(order, authorization);
    }

    @DeleteMapping("/orders/{orderId}")
    @ApiOperation(value = "Delete existing Order in the CoffeeShop", response = Customer.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Order Deleted Successfully", response = Customer.class),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Could not delete Order"),
            @ApiResponse(code = 403, message = "User not Authorized")
    })
    public String deleteOrder(@PathVariable String orderId) {
        logger.info("Delete Order " + orderId);
        orderService.deleteById(orderId);
        return "Deleted Successfully";
    }

    @GetMapping("/report/orders")
    @ApiOperation(value = "Generate Sales Report of CoffeeShop per Day or Week or Month", response = File.class, produces = "attachment; file=sales_report.csv")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CSV Report Generated Successfully", response = Customer.class),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Could not Generate Report"),
            @ApiResponse(code = 403, message = "User not Authorized")
    })
    public void getReport(HttpServletResponse response, @RequestParam(required = false) @ApiParam(value = "Date : used to get Sales Report Per Date", name = "Date") String date, @RequestParam(required = false) @ApiParam(value = "Week : used to get Sales Report per DATE", name = "Week") String week, @RequestParam(required = false) @ApiParam(value = "MOnth : used to get Sales Report per Month", name = "Month") String month, @RequestParam(required = false) @ApiParam(value = "Year : used to get Sales Report per year", name = "Year") String year) throws ParseException, IOException, CustomException {
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
    @ApiOperation(value = "Send the Invoice to the Customer for the mentioned OrderNumber")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sent Invoice over Email successfully"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Could not Send email"),
            @ApiResponse(code = 403, message = "User not Authorized")
    })
    public void sendInVoiceViaEmail(@PathVariable String orderId) throws CustomException {
        Order byId = orderService.findById(orderId);
        emailService.sendSimpleMessage(byId);
    }

}
