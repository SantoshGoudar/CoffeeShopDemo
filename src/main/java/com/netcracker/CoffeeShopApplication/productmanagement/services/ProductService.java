package com.netcracker.CoffeeShopApplication.productmanagement.services;

import com.netcracker.CoffeeShopApplication.exceptions.CustomException;
import com.netcracker.CoffeeShopApplication.productmanagement.models.Product;
import com.netcracker.CoffeeShopApplication.productmanagement.repository.ProductRepository;
import com.sun.scenario.effect.impl.prism.PrDrawable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(String id) throws CustomException {
        return productRepository.findById(id).orElseThrow(() -> new CustomException("product not found with name " + id));
        
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void delete(String id) {
        productRepository.deleteById(id);
    }
}
