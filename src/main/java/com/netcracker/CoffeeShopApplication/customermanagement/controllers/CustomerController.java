package com.netcracker.CoffeeShopApplication.customermanagement.controllers;

import com.netcracker.CoffeeShopApplication.exceptions.CustomException;
import com.netcracker.CoffeeShopApplication.customermanagement.models.Customer;
import com.netcracker.CoffeeShopApplication.customermanagement.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/coffeeshop")
public class CustomerController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    CustomerRepository repository;

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        logger.info(" list all Customers");
        return repository.findAll();
    }

    @PostMapping("/customers")
    public Customer addCustomer(@RequestBody @Valid Customer customer) {
        logger.info(" add a new customer with ID " + customer.getPhone());
        return repository.save(customer);
    }

    @GetMapping("/customers/{id}")
    public Customer getOne(@PathVariable String id) throws Exception {
        logger.info(" find one customer by ID " + id);

        Optional<Customer> byId = repository.findById(id);
        if (!byId.isPresent()) {
            logger.error("No csutomer with given phone number present " + id);
            throw new CustomException("No customer present with phone number " + id);
        }
        return byId.get();
    }

    @PutMapping("/customers")
    public Customer update(@RequestBody @Valid Customer customer) {
        logger.info("update customer");
        return repository.save(customer);

    }

    @DeleteMapping("/customers/{id}")
    public String delete(@PathVariable String id) {
        logger.info("delete customer with ID " + id);
        repository.deleteById(id);
        return "Deleted succesfully";
    }
}
