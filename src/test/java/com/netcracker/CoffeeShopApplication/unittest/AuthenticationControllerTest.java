package com.netcracker.CoffeeShopApplication.unittest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.CoffeeShopApplication.authenticationservice.controllers.AuthenticationController;
import com.netcracker.CoffeeShopApplication.authenticationservice.controllers.AuthenticationTokenProvider;
import com.netcracker.CoffeeShopApplication.authenticationservice.model.User;
import com.netcracker.CoffeeShopApplication.authenticationservice.repository.UserRepository;
import com.netcracker.CoffeeShopApplication.authenticationservice.service.AuthenicationService;
import com.netcracker.CoffeeShopApplication.authenticationservice.service.UserService;
import com.netcracker.CoffeeShopApplication.security.UserDetailsServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class AuthenticationControllerTest {
    @Autowired
    private MockMvc mvc;
    @Mock
    UserRepository repository;
    @Mock
    UserService userService;
    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    JacksonTester<User> jacksonTester;

    @Before
    public void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void testSuccessFulleLogin() throws Exception {
        User dbUser = new User("santu", "$2a$10$6rUUdVdLcrDuaDBUGvtVNuOlblrkcRyYcfOp1ZqpVUFM336frpnd6", "ADMIN");
        User user = new User("santu", "santu", "ADMIN");
        Mockito.when(repository.findById("santu")).thenReturn(Optional.of(user));
        Mockito.when(userService.findbyId("santu")).thenReturn(dbUser);
        MockHttpServletRequestBuilder content = MockMvcRequestBuilders.post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(jacksonTester.write(user).getJson());
        MockHttpServletResponse response = mvc.perform(content).andReturn().getResponse();
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
        Assert.assertNotNull(response.getHeader("Authorization"));
    }
}
