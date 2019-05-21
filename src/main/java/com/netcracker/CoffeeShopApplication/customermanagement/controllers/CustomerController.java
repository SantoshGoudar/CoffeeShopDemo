package com.netcracker.CoffeeShopApplication.customermanagement.controllers;

import com.netcracker.CoffeeShopApplication.constants.StringConstants;
import com.netcracker.CoffeeShopApplication.customermanagement.service.CustomerService;
import com.netcracker.CoffeeShopApplication.exceptions.CustomException;
import com.netcracker.CoffeeShopApplication.customermanagement.models.Customer;
import com.netcracker.CoffeeShopApplication.customermanagement.repository.CustomerRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(StringConstants.CUSTOMERS)
@Api(value = "CustomerMangement API", produces = "application/json", description = "All Customer Management related APIS of CoffeeShop Application"
)
@Slf4j
public class CustomerController {

    @Autowired
    CustomerService service;

    @GetMapping()
    @ApiOperation(value = "Retrieves all the customers of CoffeeShop", response = Customer.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Customers  Retrieved", response = Customer.class),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Customers not found"),
            @ApiResponse(code = 403, message = "User not Authorized")
    })

    public List<Customer> getAllCustomers() {
        log.info(" list all Customers");
        return service.findAll();
    }

    @PostMapping()
    @ApiOperation(value = "Add new Customer to the CoffeeShop", response = Customer.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Customer added to DB", response = Customer.class),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Could not add Customer"),
            @ApiResponse(code = 403, message = "User not Authorized")
    })

    public Customer addCustomer(@RequestBody @Valid Customer customer) {
        log.info(" add a new customer with ID " + customer.getPhone());
        return service.save(customer);
    }

    @GetMapping(StringConstants.CUSTOMER_ID)
    @ApiOperation(value = "Get Customer with Phone number ", response = Customer.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get Customer  ", response = Customer.class),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Could not get Customer"),
            @ApiResponse(code = 403, message = "User not Authorized")
    })
    public Customer getOne(@PathVariable String id) throws Exception {
        log.info(" find one customer by ID " + id);
        return service.findById(id);
    }

    @PutMapping()
    public Customer update(@RequestBody @Valid Customer customer) {
        log.info("update customer");
        return service.save(customer);

    }

    @DeleteMapping(StringConstants.CUSTOMER_ID)
    @ApiOperation(value = "Delete Customer with Phone number ", response = Customer.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deleted Customer successfully", response = Customer.class),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Could not get Customer"),
            @ApiResponse(code = 403, message = "User not Authorized")
    })
    public String delete(@PathVariable String id) {
        log.info("delete customer with ID " + id);
        service.deleteById(id);
        return "Deleted succesfully";
    }
}
