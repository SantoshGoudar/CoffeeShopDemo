package com.netcracker.CoffeeShopApplication.ordermanagement.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.CoffeeShopApplication.customermanagement.models.Customer;
import com.netcracker.CoffeeShopApplication.productmanagement.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Set;

@Service
public class RestServiceCaller {

    private RestTemplate restTemplate;

    @Autowired
    RestServiceCaller(RestTemplateBuilder builder) {
        restTemplate = builder.build();
    }

    public com.netcracker.CoffeeShopApplication.ordermanagement.models.Customer getCustomerObject(String endPoint, Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        for (Map.Entry<String, String> entry :
                headers.entrySet()) {
            httpHeaders.add(entry.getKey(), entry.getValue());
        }
        HttpEntity entity = new HttpEntity(null, httpHeaders);
        Customer body = null;
        try {
            ResponseEntity<Customer> response = restTemplate.exchange(
                    endPoint, HttpMethod.GET, entity, Customer.class);
            body = response.getBody();
        } catch (Exception e) {

        }

        if (body == null) {
            return null;
        }
        return new com.netcracker.CoffeeShopApplication.ordermanagement.models.Customer(body.getName(), body.getEmail(), body.getPhone(), body.getAddress());

    }

    public com.netcracker.CoffeeShopApplication.ordermanagement.models.Customer putCustomerObject(String endPoint, Map<String, String> headers, com.netcracker.CoffeeShopApplication.ordermanagement.models.Customer newCustomer) {
        HttpHeaders httpHeaders = new HttpHeaders();
        for (Map.Entry<String, String> entry :
                headers.entrySet()) {
            httpHeaders.add(entry.getKey(), entry.getValue());
        }
        HttpEntity entity = new HttpEntity(newCustomer, httpHeaders);

        ResponseEntity<Customer> response = restTemplate.exchange(
                endPoint, HttpMethod.POST, entity, Customer.class);
        Customer body = response.getBody();
        if (body == null) {
            return null;
        }
        return new com.netcracker.CoffeeShopApplication.ordermanagement.models.Customer(body.getName(), body.getEmail(), body.getPhone(), body.getAddress());

    }

    public Product getProductObject(String endPoint, Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        for (Map.Entry<String, String> entry :
                headers.entrySet()) {
            httpHeaders.add(entry.getKey(), entry.getValue());
        }
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<Product> response = restTemplate.exchange(
                endPoint, HttpMethod.GET, entity, Product.class);
        Product body = response.getBody();
        return body;
    }
}
