package com.netcracker.CoffeeShopApplication.graphql.fetchers;

import com.netcracker.CoffeeShopApplication.productmanagement.models.Product;
import com.netcracker.CoffeeShopApplication.productmanagement.repository.ProductRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MutationProductFetcher implements DataFetcher<Product> {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Product get(DataFetchingEnvironment dataFetchingEnvironment) {
        return productRepository.save((Product) dataFetchingEnvironment.getArgument("product"));
        
    }
}
