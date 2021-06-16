package com.netcracker.CoffeeShopApplication.graphql.fetchers;

import com.fasterxml.jackson.databind.ObjectMapper;
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

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String product = objectMapper.writeValueAsString(dataFetchingEnvironment.getArgument("product"));
            return productRepository.save(objectMapper.readValue(product,Product.class));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
