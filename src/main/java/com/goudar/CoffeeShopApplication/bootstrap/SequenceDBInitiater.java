package com.goudar.CoffeeShopApplication.bootstrap;

import com.goudar.CoffeeShopApplication.ordermanagement.services.OrderService;
import com.goudar.CoffeeShopApplication.ordermanagement.services.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

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
