package com.netcracker.CoffeeShopApplication.graphql.fetchers;

import java.util.List;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.netcracker.CoffeeShopApplication.productmanagement.models.Product;
import com.netcracker.CoffeeShopApplication.productmanagement.services.ProductService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
public class ProductsFetcher implements GraphQLQueryResolver {

    @Autowired
    ProductService productService;

    public List<Product> getProducts() {
        return productService.findAll();
    }

    public Product getProduct(String productId) throws Exception {
        return productService.findById(productId);

    }
}
