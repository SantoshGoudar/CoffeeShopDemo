package com.netcracker.CoffeeShopApplication.unittest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.CoffeeShopApplication.constants.StringConstants;
import com.netcracker.CoffeeShopApplication.ordermanagement.controllers.EmailService;
import com.netcracker.CoffeeShopApplication.ordermanagement.controllers.OrderController;
import com.netcracker.CoffeeShopApplication.ordermanagement.models.Customer;
import com.netcracker.CoffeeShopApplication.ordermanagement.models.Item;
import com.netcracker.CoffeeShopApplication.ordermanagement.models.Order;
import com.netcracker.CoffeeShopApplication.ordermanagement.services.CSVReportGenerator;
import com.netcracker.CoffeeShopApplication.ordermanagement.services.IReportGenerator;
import com.netcracker.CoffeeShopApplication.ordermanagement.services.OrderService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Date;

@RunWith(SpringRunner.class)
@WebMvcTest(value = OrderController.class, secure = false)
public class OrderControllerTest {
    @Autowired
    MockMvc mvc;
    @MockBean
    OrderService orderService;
    @InjectMocks
    OrderController controller;
    @MockBean
    CSVReportGenerator reportGenerator;
    @MockBean
    EmailService emailService;
    Order order;
    JacksonTester<Order> jacksonTester;


    @Before
    public void before() {
        JacksonTester.initFields(this, new ObjectMapper());
        order = new Order();
        order.setOrderId("CS1");
        order.setDate(null);
        order.setCustomer(new Customer("Santu", "San@gmail.com", "0355124349", null));
        order.setItems(Arrays.asList(new Item("ColdCoffe", 2, 450)));
    }

    @Test
    public void testGetAllOrders() throws Exception {
        Mockito.when(orderService.listAll()).thenReturn(Arrays.asList(order));
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.get(StringConstants.ORDERS).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        Assert.assertEquals("[" + jacksonTester.write(order).getJson() + "]", response.getContentAsString());
    }

    @Test
    public void testGetOrderWithID() throws Exception {
        Mockito.when(orderService.findById(order.getOrderId())).thenReturn(order);
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.get(StringConstants.ORDERS + "/" + order.getOrderId()).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        Assert.assertEquals(jacksonTester.write(order).getJson(), response.getContentAsString());
    }

    @Test
    public void testAddNewOrder() throws Exception {
        Mockito.when(orderService.save(Mockito.any(Order.class),Mockito.anyString())).thenReturn(order);
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.post(StringConstants.ORDERS).header("Authorization", "auth").contentType(MediaType.APPLICATION_JSON).content(jacksonTester.write(order).getJson()).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        Assert.assertEquals(jacksonTester.write(order).getJson(), response.getContentAsString());
    }

    @Test
    public void testUpdateOrder() throws Exception {
        Mockito.when(orderService.save(Mockito.any(Order.class),Mockito.anyString())).thenReturn(order);
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.put(StringConstants.ORDERS).header("Authorization", "auth").contentType(MediaType.APPLICATION_JSON).content(jacksonTester.write(order).getJson()).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        Assert.assertEquals(jacksonTester.write(order).getJson(), response.getContentAsString());
    }

    @Test
    public void testDeleteOrder() throws Exception {
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.delete(StringConstants.ORDERS + "/" + order.getOrderId()).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        Assert.assertEquals("Deleted Successfully", response.getContentAsString());
    }
}
