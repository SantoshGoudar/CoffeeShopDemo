package com.netcracker.CoffeeShopApplication.productmanagement.controllers;

import com.netcracker.CoffeeShopApplication.constants.StringConstants;
import com.netcracker.CoffeeShopApplication.exceptions.CustomException;
import com.netcracker.CoffeeShopApplication.productmanagement.models.Product;
import com.netcracker.CoffeeShopApplication.productmanagement.repository.ProductRepository;
import com.netcracker.CoffeeShopApplication.productmanagement.services.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(StringConstants.PATH_SEPERATOR)
@Api(value = "Product Management API", description = ("Product Management API for CoffeeShop"), authorizations = {@Authorization(value = "ADMIN")})
@Slf4j
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping(StringConstants.PRODUCTS)
    @ApiOperation(value = "Get All Products in the CoffeeShop",tags = {"Product"})
    public List<Product> listAll() {
        log.info(" list all prodcuts ");
        return productService.findAll();
    }

    @GetMapping(StringConstants.PRODUCTS_WITH_NAME)
    @ApiOperation(value = "Get Product with the specified Name",tags = {"Product"})
    public Product getProduct(@PathVariable String name) throws CustomException {
        log.info("l get product " + name);
        return productService.findById(name);

    }

    @PostMapping(StringConstants.PRODUCTS)
    @ApiOperation(value="Add a new Product to the CoffeeShop",tags = {"Product"})
    public Product addProduct(@RequestBody @Valid Product product ) {
        log.info("add new product " + product.getName());

        return productService.save(product);
    }

    @PutMapping(StringConstants.PRODUCTS)
    @ApiOperation(value="Update Exisitng product",tags = {"Product"})
    public Product updateProduct(@RequestBody @Valid Product product) {
        log.info("update product " + product.getName());
        return productService.save(product);
    }

    @DeleteMapping(StringConstants.PRODUCTS_WITH_NAME)
    @ApiOperation(value="Delete a Product with the given name",tags = {"Product"})
    public String deleteProduct(@PathVariable String name) {
        log.info("Delete product " + name);
        productService.delete(name);
        return "Deleted Successfully";
    }
}
