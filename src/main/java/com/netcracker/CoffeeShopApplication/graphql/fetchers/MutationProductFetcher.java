package com.netcracker.CoffeeShopApplication.graphql.fetchers;

import com.netcracker.CoffeeShopApplication.productmanagement.models.Product;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;

@Component
public class MutationProductFetcher implements DataFetcher<Product> {

    @Override
    public Product get(DataFetchingEnvironment dataFetchingEnvironment) {
        return null;
    }
}
