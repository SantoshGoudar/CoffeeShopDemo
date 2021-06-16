package com.netcracker.CoffeeShopApplication.ordermanagement.controllers;


import com.netcracker.CoffeeShopApplication.constants.StringConstants;
import com.netcracker.CoffeeShopApplication.exceptions.CustomException;
import com.netcracker.CoffeeShopApplication.ordermanagement.models.Customer;
import com.netcracker.CoffeeShopApplication.ordermanagement.models.Order;
import com.netcracker.CoffeeShopApplication.ordermanagement.services.EmailService;
import com.netcracker.CoffeeShopApplication.ordermanagement.services.IReportGenerator;
import com.netcracker.CoffeeShopApplication.ordermanagement.services.OrderService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(StringConstants.PATH_SEPERATOR)
@Api(value = "Order Management API", description = "All Order Related API along with Support for Sales Report, Invoice Generation, Email service")
@Slf4j
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    IReportGenerator reportGenerator;
    @Autowired
    EmailService emailService;

    @GetMapping(StringConstants.ORDERS)
    @ApiOperation(value = "List All Orders According to the date,week,month or year", response = com.netcracker.CoffeeShopApplication.ordermanagement.models.Customer.class, tags = {"Order"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Listed Orders Successfully", response = Customer.class),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Could not get Orders"),
            @ApiResponse(code = 403, message = "User not Authorized")
    })
    public List<Order> listAllOrders(@RequestParam(required = false) @ApiParam(value = "Date : used to query Orders per DATE", name = "Date") String date,
                                     @RequestParam(required = false) @ApiParam(name = "Week", value = "Week : Calendar week Number per year : to get Orders per week") String week,
                                     @RequestParam(required = false) @ApiParam(name = "Month", value = "Month no : for getting Orders per month") String month,
                                     @RequestParam(required = false) @ApiParam(name = "Year", value = "Year : to get the Orders per year") String year) throws ParseException {
        log.info("list all orders called");
        if (date != null) {
            log.info("list all orders per date " + date);
            return orderService.listOrdersPerDate(date);
        }
        if (week != null) {
            log.info("list all orders per week " + week);
            return orderService.listOrdersPerWeek(Integer.parseInt(week));
        }
        if (month != null) {
            log.info("list all orders per month " + month);
            return orderService.listOrdersPerMonth(Integer.parseInt(month));
        }
        if (year != null) {
            log.info("list all orders per year " + year);
            return orderService.listOrdersPerYear(year);
        }
        return orderService.listAll();
    }

    @GetMapping(StringConstants.ORDERS_WITH_ID)
    @ApiOperation(value = "Get a Order with Order Number specified", response = Customer.class, tags = {"Order"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Got the Order with Order number", response = Customer.class),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Could not get Order"),
            @ApiResponse(code = 403, message = "User not Authorized")
    })
    public Order getOrder(@PathVariable String orderId) throws CustomException {
        log.info("get order by orderID  " + orderId);
        Order byId = orderService.findById(orderId);

        return byId;
    }

    @PostMapping(StringConstants.ORDERS)
    @ApiOperation(value = "Update existing Order in the CoffeeShop", response = Customer.class, tags = {"Order"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Order Added Successfully", response = Customer.class),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Could not add Order"),
            @ApiResponse(code = 403, message = "User not Authorized")
    })
    public Order updateOrder(@RequestBody @Valid Order order, HttpServletRequest request) throws CustomException{
        log.info("update order " + order.getOrderId());
        String authorization = request.getHeader("Authorization");
        return orderService.save(order, authorization);
    }

    @PutMapping(StringConstants.ORDERS)
    @ApiOperation(value = "Add new Order to CoffeeShop", response = Customer.class, tags = {"Order"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Order Added Successfully", response = Customer.class),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Could not add Order"),
            @ApiResponse(code = 403, message = "User not Authorized")
    })
    public Order addOrder(@RequestBody @Valid Order order, HttpServletRequest request) throws CustomException{
        log.info("Add new Order ");
        String authorization = request.getHeader("Authorization");
        return orderService.save(order, authorization);
    }

    @DeleteMapping(StringConstants.ORDERS_WITH_ID)
    @ApiOperation(value = "Delete existing Order in the CoffeeShop", response = Customer.class, tags = {"Order"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Order Deleted Successfully", response = Customer.class),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Could not delete Order"),
            @ApiResponse(code = 403, message = "User not Authorized")
    })
    public String deleteOrder(@PathVariable String orderId) {
        log.info("Delete Order " + orderId);
        orderService.deleteById(orderId);
        return "Deleted Successfully";
    }

    @GetMapping(StringConstants.REPORT_ORDERS)
    @ApiOperation(value = "Generate Sales Report of CoffeeShop per Day or Week or Month", response = File.class, produces = "attachment; file=sales_report.csv", tags = {"Report"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CSV Report Generated Successfully", response = Customer.class),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Could not Generate Report"),
            @ApiResponse(code = 403, message = "User not Authorized")
    })
    public void getReport(HttpServletResponse response, @RequestParam(required = false) @ApiParam(value = "Date : used to get Sales Report Per Date", name = "Date") String date, @RequestParam(required = false) @ApiParam(value = "Week : used to get Sales Report per DATE", name = "Week") String week, @RequestParam(required = false) @ApiParam(value = "MOnth : used to get Sales Report per Month", name = "Month") String month, @RequestParam(required = false) @ApiParam(value = "Year : used to get Sales Report per year", name = "Year") String year) throws ParseException, IOException, CustomException {

        response.setContentType(new MediaType("text", "csv").getType());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; file=sales_report.csv");
        log.info("report generation called ");
        List<Order> orders;

        if (date != null) {
            orders = orderService.listOrdersPerDate(date);
            if (orders != null && !orders.isEmpty()) {
                log.info("report generation called for date " + date);
                reportGenerator.writeDailyReport(response.getWriter(), orders);
            }
        }
        if (week != null) {
            orders = orderService.listOrdersPerWeek(Integer.parseInt(week));
            if (orders != null && !orders.isEmpty()) {
                log.info("report generation called for week " + week);
                reportGenerator.writeWeeklyReport(response.getWriter(), orders);
            }
        }
        if (month != null) {
            log.info("report generation called for month " + month);
            orders = orderService.listOrdersPerMonth(Integer.parseInt(month));
            if (orders != null && !orders.isEmpty()) {
                reportGenerator.writeMonthlyReport(response.getWriter(), orders);
            }
        }
        if (year != null) {
            log.info("report generation called for year " + year);
            orders = orderService.listOrdersPerYear(year);
            if (orders != null && !orders.isEmpty()) {
                reportGenerator.writeMonthlyReport(response.getWriter(), orders);
            }
        }


    }

    @GetMapping(StringConstants.INVOICE)
    @ApiOperation(value = "Send the Invoice to the Customer for the mentioned OrderNumber", tags = {"Invoice"})
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
