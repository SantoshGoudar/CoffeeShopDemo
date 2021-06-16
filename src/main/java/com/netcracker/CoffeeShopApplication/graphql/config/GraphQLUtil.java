package com.netcracker.CoffeeShopApplication.graphql.config;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import com.netcracker.CoffeeShopApplication.graphql.fetchers.MutationProductFetcher;
import com.netcracker.CoffeeShopApplication.graphql.fetchers.ProductFetcher;
import com.netcracker.CoffeeShopApplication.graphql.fetchers.ProductsFetcher;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class GraphQLUtil {

    @Autowired
    ProductFetcher productFetcher;
    @Autowired
    ProductsFetcher productsFetcher;
    @Autowired
    MutationProductFetcher mutationProductFetcher;
    @Value("classpath:models.graphqls")
    Resource schemaFile;

   @Bean(name = "graphQLSchema")
    public GraphQLSchema createGraphQL() throws IOException {
        File file = schemaFile.getFile();
        TypeDefinitionRegistry parse = new SchemaParser().parse(file);
        GraphQLSchema graphQLSchema = new SchemaGenerator().makeExecutableSchema(parse, buildRuntimeWiring());
        return graphQLSchema;
    }

    public RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring().type("Query",
            typeWiring -> typeWiring.dataFetcher("product", productFetcher).dataFetcher("products", productsFetcher))
            .type("Mutation", typeWiring -> typeWiring.dataFetcher("createProduct", mutationProductFetcher)).build();
    }
}
