package com.netcracker.CoffeeShopApplication.unittest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.CoffeeShopApplication.constants.StringConstants;
import com.netcracker.CoffeeShopApplication.exceptions.CustomException;
import com.netcracker.CoffeeShopApplication.productmanagement.controllers.ProductController;
import com.netcracker.CoffeeShopApplication.productmanagement.models.Category;
import com.netcracker.CoffeeShopApplication.productmanagement.models.Product;
import com.netcracker.CoffeeShopApplication.productmanagement.repository.ProductRepository;
import com.netcracker.CoffeeShopApplication.productmanagement.services.ProductService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ProductController.class, secure = false)
public class ProductControllerTest {
    @Autowired
    MockMvc mvc;
    @MockBean
    ProductService service;
    @InjectMocks
    ProductController controller;
    JacksonTester<Product> jacksonTester;

    Product product;

    @Before
    public void setUp() {

        product = new Product();
        product.setCategory(Category.COLD_COFFEE);
        product.setName("COLD_COFFE");
        product.setPrice(235);
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void testGetAllProducts() throws Exception {
        Mockito.when(service.findAll()).thenReturn(Arrays.asList(product));
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.get(StringConstants.PRODUCTS).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        Assert.assertEquals("[" + jacksonTester.write(product).getJson() + "]", response.getContentAsString());
    }

    @Test
    public void testGetProductWithID() throws Exception {
        Mockito.when(service.findById("COLD_COFFE")).thenReturn(product);
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.get(StringConstants.PRODUCTS + "/COLD_COFFE").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        Assert.assertEquals(jacksonTester.write(product).getJson(), response.getContentAsString());
    }
    @Test
    public void testGetProductWithIDNonExisitng() throws Exception {
        Mockito.when(service.findById("COLD_COFE")).thenThrow(new CustomException("Not found"));
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.get(StringConstants.PRODUCTS + "/COLD_COFE").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());

    }
    @Test
    public void testAddProduct() throws Exception {
        Mockito.when(service.save(Mockito.any(Product.class))).thenReturn(product);
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.post(StringConstants.PRODUCTS).contentType(MediaType.APPLICATION_JSON).content(jacksonTester.write(product).getJson()).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        Assert.assertEquals(jacksonTester.write(product).getJson(), response.getContentAsString());
    }

    @Test
    public void testUpdateProduct() throws Exception {
        Mockito.when(service.save(Mockito.any(Product.class))).thenReturn(product);
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.put(StringConstants.PRODUCTS).contentType(MediaType.APPLICATION_JSON).content(jacksonTester.write(product).getJson()).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        Assert.assertEquals(jacksonTester.write(product).getJson(), response.getContentAsString());
    }

    @Test
    public void testDeleteProduct() throws Exception {

        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.delete(StringConstants.PRODUCTS + "/COLD_COFFE").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        Assert.assertEquals("Deleted Successfully", response.getContentAsString());
    }
}
