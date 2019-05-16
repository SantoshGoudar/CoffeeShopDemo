package com.netcracker.CoffeeShopApplication.productmanagement.controllers;

import com.netcracker.CoffeeShopApplication.exceptions.CustomException;
import com.netcracker.CoffeeShopApplication.productmanagement.models.Product;
import com.netcracker.CoffeeShopApplication.productmanagement.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/coffeeshop")
public class ProductController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    ProductRepository repository;

    @GetMapping("/products")
    public List<Product> listAll() {
        logger.info(" list all prodcuts ");
        return repository.findAll();
    }

    @GetMapping("/products/{name}")
    public Product getProduct(@PathVariable String name) throws CustomException {
        logger.info("l get product " + name);
        Optional<Product> byId = repository.findById(name);
        if (!byId.isPresent()) {
            logger.error("Product not found with name " + name
            );
            throw new CustomException("Product not present with the Name" + name);
        }
        return byId.get();

    }

    @PutMapping("/products")
    public Product addProduct(@RequestBody @Valid Product product) {
        logger.info("add new product " + product.getName());
        return repository.save(product);
    }

    @PostMapping("/products")
    public Product updateProduct(@RequestBody @Valid Product product) {
        logger.info("update product " + product.getName());
        return repository.save(product);
    }

    @DeleteMapping("/products/{name}")
    public String deleteProduct(@PathVariable String name) {
        logger.info("Delete product " + name);
        repository.deleteById(name);
        return "Deleted Successfully";
    }
}
