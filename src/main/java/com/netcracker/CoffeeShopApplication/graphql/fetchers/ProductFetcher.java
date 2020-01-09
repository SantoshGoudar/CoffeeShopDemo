package com.netcracker.CoffeeShopApplication.graphql.fetchers;

import com.netcracker.CoffeeShopApplication.exceptions.CustomException;
import com.netcracker.CoffeeShopApplication.productmanagement.models.Product;
import com.netcracker.CoffeeShopApplication.productmanagement.repository.ProductRepository;
import com.netcracker.CoffeeShopApplication.productmanagement.services.ProductService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
public class ProductFetcher implements DataFetcher<Product> {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Product get(DataFetchingEnvironment dataFetchingEnvironment) {
        return productRepository.findById((String) dataFetchingEnvironment.getArgument("name")).get();
    }
}
