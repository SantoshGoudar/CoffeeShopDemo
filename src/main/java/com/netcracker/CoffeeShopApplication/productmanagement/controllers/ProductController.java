package com.netcracker.CoffeeShopApplication.productmanagement.controllers;

import com.netcracker.CoffeeShopApplication.exceptions.CustomException;
import com.netcracker.CoffeeShopApplication.productmanagement.models.Product;
import com.netcracker.CoffeeShopApplication.productmanagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/coffeeshop")
public class ProductController {
    @Autowired
    ProductRepository repository;

    @GetMapping("/products")
    public List<Product> listAll() {
        return repository.findAll();
    }

    @GetMapping("/products/{name}")
    public Product getProduct(@PathVariable String name) throws CustomException {

        Optional<Product> byId = repository.findById(name);
        if(!byId.isPresent()){
            throw new CustomException("Product not present with the Name"+ name);
        }
        return byId.get();

    }

    @PutMapping("/products")
    public Product addProduct(@RequestBody @Valid Product product) {
        return repository.save(product);
    }

    @PostMapping("/products")
    public Product updateProduct(@RequestBody @Valid Product product) {
        return repository.save(product);
    }

    @DeleteMapping("/products/{name}")
    public String deleteProduct(@PathVariable String name) {
        repository.deleteById(name);
        return "Deleted Successfully";
    }
}
