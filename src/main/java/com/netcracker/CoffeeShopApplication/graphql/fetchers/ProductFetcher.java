package com.netcracker.CoffeeShopApplication.graphql.fetchers;

import com.netcracker.CoffeeShopApplication.productmanagement.models.Product;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ProductFetcher implements DataFetcher<Product> {
    @Override
    public Product get(DataFetchingEnvironment dataFetchingEnvironment) {
        return null;
    }
}
