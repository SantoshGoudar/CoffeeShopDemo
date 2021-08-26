package com.goudar.CoffeeShopApplication;

import com.goudar.CoffeeShopApplication.authenticationservice.model.User;
import com.goudar.CoffeeShopApplication.authenticationservice.repository.UserRepository;
import com.goudar.CoffeeShopApplication.authenticationservice.service.UserService;
import com.goudar.CoffeeShopApplication.constants.StringConstants;
import com.goudar.CoffeeShopApplication.customermanagement.models.Customer;
import com.goudar.CoffeeShopApplication.customermanagement.service.CustomerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

public class CoffeeShopApplicationTests {
    @MockBean
    UserRepository userRepository;
    @Autowired
    TestRestTemplate restTemplate;
    @MockBean
    CustomerService customerService;
    @MockBean
    UserService userDetailsService;

    Customer customer;

    @Before
    public void setUp() throws Exception {

        customer = new Customer();
        customer.setName("Santsoh");
        customer.setPhone("9035512345");
        customer.setEmail("email@email.com");
        customer.setAddress("Address");


    }

    private String getAuthToken() {
        User dbUser = new User("santu", "$2a$10$6rUUdVdLcrDuaDBUGvtVNuOlblrkcRyYcfOp1ZqpVUFM336frpnd6", "ADMIN");
        User user = new User("santu", "santu", "ADMIN");
        Mockito.when(userRepository.findById(dbUser.getUserName())).thenReturn(Optional.of(user));
        Mockito.when(userDetailsService.findbyId(dbUser.getUserName())).thenReturn(dbUser);
        HttpEntity entity = new HttpEntity(user);

        ResponseEntity response = restTemplate.exchange(StringConstants.AUTH +
                StringConstants.LOGIN, HttpMethod.POST, entity, Object.class);
        String authorization = response.getHeaders().getFirst("Authorization");
        return authorization;
    }

    @Test
    public void testReqestForbidden() throws Exception {

        List<Customer> allEmployees = Arrays.asList(customer);
        Mockito.when(customerService.findAll()).thenReturn(allEmployees);
        ResponseEntity<Customer> forEntity = restTemplate.getForEntity(StringConstants.CUSTOMERS, Customer.class);
        Assert.assertEquals(HttpStatus.FORBIDDEN.value(), forEntity.getStatusCodeValue());
    }

    @Test
    public void testGetAllCustomers() throws Exception {

        List<Customer> allEmployees = Arrays.asList(customer);
        Mockito.when(customerService.findAll()).thenReturn(allEmployees);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", getAuthToken());
        HttpEntity entity = new HttpEntity(null, headers);
        ResponseEntity<List> response = restTemplate.exchange(StringConstants.CUSTOMERS, HttpMethod.GET, entity, List.class);
        Assert.assertEquals(allEmployees.size(), response.getBody().size());
    }

}
