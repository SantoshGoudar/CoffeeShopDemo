package com.netcracker.CoffeeShopApplication.unittest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.CoffeeShopApplication.authenticationservice.model.User;
import com.netcracker.CoffeeShopApplication.authenticationservice.repository.UserRepository;
import com.netcracker.CoffeeShopApplication.authenticationservice.service.UserService;
import com.netcracker.CoffeeShopApplication.customermanagement.controllers.CustomerController;
import com.netcracker.CoffeeShopApplication.customermanagement.models.Customer;
import com.netcracker.CoffeeShopApplication.customermanagement.repository.CustomerRepository;
import com.netcracker.CoffeeShopApplication.customermanagement.service.CustomerService;
import com.netcracker.CoffeeShopApplication.security.UserDetailsServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CustomerController.class, secure = false)
public class CustomerControllerTest {

    @Autowired
    MockMvc mvc;
    @MockBean
    CustomerService customerService;

    Customer customer;
    JacksonTester<Customer> jacksonTester;


    @Before
    public void setUp() throws Exception {
        JacksonTester.initFields(this, new ObjectMapper());
        customer = new Customer();
        customer.setName("Santsoh");
        customer.setPhone("9035512345");
        customer.setEmail("email@email.com");
        customer.setAddress("Address");


    }

    @Test
    public void testGetAllCustomers() throws Exception {

        List<Customer> allEmployees = Arrays.asList(customer);
        Mockito.when(customerService.findAll()).thenReturn(allEmployees);
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.get("/customers").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        Assert.assertEquals("[" + jacksonTester.write(customer).getJson() + "]", response.getContentAsString());
    }

    @Test
    public void testGetCustomerWithID() throws Exception {


        Mockito.when(customerService.findById(customer.getPhone())).thenReturn(customer);
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.get("/customers/" + customer.getPhone()).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        Assert.assertEquals(jacksonTester.write(customer).getJson(), response.getContentAsString());
    }

    @Test
    public void testAddNewCustomer() throws Exception {


        Mockito.when(customerService.save(Mockito.any(Customer.class))).thenReturn(customer);
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.post("/customers").contentType(MediaType.APPLICATION_JSON).content(jacksonTester.write(customer).getJson()).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        Assert.assertEquals(jacksonTester.write(customer).getJson(), response.getContentAsString());
    }

    @Test
    public void testUpdateCustomer() throws Exception {


        Mockito.when(customerService.save(Mockito.any(Customer.class))).thenReturn(customer);
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.put("/customers").contentType(MediaType.APPLICATION_JSON).content(jacksonTester.write(customer).getJson()).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        Assert.assertEquals(jacksonTester.write(customer).getJson(), response.getContentAsString());
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.delete("/customers/" + customer.getPhone()).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        Assert.assertEquals("Deleted Succesfully", response.getContentAsString());
    }
}
