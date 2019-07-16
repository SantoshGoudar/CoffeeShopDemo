package com.netcracker.CoffeeShopApplication.bootstrap;

import com.netcracker.CoffeeShopApplication.authenticationservice.model.User;
import com.netcracker.CoffeeShopApplication.authenticationservice.service.UserService;
import com.netcracker.CoffeeShopApplication.ordermanagement.models.Customer;
import com.netcracker.CoffeeShopApplication.ordermanagement.models.Item;
import com.netcracker.CoffeeShopApplication.ordermanagement.models.Order;
import com.netcracker.CoffeeShopApplication.ordermanagement.services.OrderService;
import com.netcracker.CoffeeShopApplication.ordermanagement.services.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Component
public class SequenceDBInitiater implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    SequenceService sequenceService;
    @Autowired
    OrderService orderService;

    private String seqKey = "OrderSequence";

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        if (!sequenceService.sequenceExist(seqKey)) {
            sequenceService.saveNewSequence(seqKey, 0);
        }
        

    }
}
