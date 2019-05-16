package com.netcracker.CoffeeShopApplication.bootstrap;

import com.netcracker.CoffeeShopApplication.authenticationservice.model.User;
import com.netcracker.CoffeeShopApplication.authenticationservice.service.UserService;
import com.netcracker.CoffeeShopApplication.ordermanagement.services.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class SequenceDBInitiater implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    SequenceService sequenceService;

    private String seqKey = "OrderSequence";

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        if (!sequenceService.sequenceExist(seqKey)) {
            sequenceService.saveNewSequence(seqKey, 0);
        }

    }
}
