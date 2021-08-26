package com.goudar.CoffeeShopApplication.customermanagement.controllers;

import com.goudar.CoffeeShopApplication.constants.StringConstants;
import com.goudar.CoffeeShopApplication.customermanagement.service.CustomerService;
import com.goudar.CoffeeShopApplication.customermanagement.models.Customer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(StringConstants.CUSTOMERS)
@Api(value = "CustomerMangement API", produces = "application/json", description = "All Customer Management related APIS of CoffeeShop Application"
)
@Slf4j
public class CustomerController {

    @Autowired
    CustomerService service;

    @GetMapping()
    @ApiOperation(value = "Retrieves all the customers of CoffeeShop", tags = {"Customer"})
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
    @ApiOperation(value = "Add new Customer to the CoffeeShop", response = Customer.class, tags = {"Customer"})
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
    @ApiOperation(value = "Get Customer with Phone number ", response = Customer.class, tags = {"Customer"})
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
    @ApiOperation(value = "Update Customer ", response = Customer.class, tags = {"Customer"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updated Successfully ", response = Customer.class),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Could not get Customer"),
            @ApiResponse(code = 403, message = "User not Authorized")
    })
    public Customer update(@RequestBody @Valid Customer customer) {
        log.info("update customer");
        return service.save(customer);

    }

    @DeleteMapping(StringConstants.CUSTOMER_ID)
    @ApiOperation(value = "Delete Customer with Phone number ", response = Customer.class, tags = {"Customer"})
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
