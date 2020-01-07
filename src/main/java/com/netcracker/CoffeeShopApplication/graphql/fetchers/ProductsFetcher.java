package com.netcracker.CoffeeShopApplication.graphql.fetchers;

import java.util.List;
import com.netcracker.CoffeeShopApplication.productmanagement.models.Product;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ProductsFetcher implements DataFetcher<List<Product>> {

    @Override
    public List<Product> get(DataFetchingEnvironment dataFetchingEnvironment) {
        return null;
    }
}
