package com.netcracker.CoffeeShopApplication.graphql.fetchers;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.netcracker.CoffeeShopApplication.productmanagement.models.Category;
import com.netcracker.CoffeeShopApplication.productmanagement.models.Product;
import com.netcracker.CoffeeShopApplication.productmanagement.services.ProductService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MutationProductFetcher implements GraphQLMutationResolver {
    @Autowired
    ProductService productService;

    public Product createProduct(Product product) {
        return productService.save(product);
    }
}
