package com.netcracker.CoffeeShopApplication.customermanagement.controllers;

import com.netcracker.CoffeeShopApplication.exceptions.CustomException;
import com.netcracker.CoffeeShopApplication.customermanagement.models.Customer;
import com.netcracker.CoffeeShopApplication.customermanagement.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/coffeeshop")
public class CustomerController {
    @Autowired
    CustomerRepository repository;

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    @PostMapping("/customers")
    public Customer addCustomer(@RequestBody@Valid Customer customer) {
        return repository.save(customer);
    }

    @GetMapping("/customers/{id}")
    public Customer getOne(@PathVariable String id) throws Exception{
        Optional<Customer> byId = repository.findById(id);
        if(!byId.isPresent()){
            throw new CustomException("No customer present with phone number "+id);
        }
        return byId.get();
    }

    @PutMapping("/customers")
    public Customer update(@RequestBody @Valid Customer customer) {
        return repository.save(customer);
    }

    @DeleteMapping("/customers/{id}")
    public String delete(@PathVariable String id) {
        repository.deleteById(id);
        return "Deleted succesfully";
    }
}
