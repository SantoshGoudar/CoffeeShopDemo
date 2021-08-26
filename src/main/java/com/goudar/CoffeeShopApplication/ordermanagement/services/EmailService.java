package com.goudar.CoffeeShopApplication.ordermanagement.services;

import com.goudar.CoffeeShopApplication.exceptions.CustomException;
import com.goudar.CoffeeShopApplication.ordermanagement.models.Order;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class EmailService { //todo; move to service package
    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private Configuration freemarkerConfig;

    public void sendSimpleMessage(Order order) throws CustomException {
        log.info("Send email invoice");
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());


            Template t = freemarkerConfig.getTemplate("EmailTemplate.html");
            Map<String, Object> model = new HashMap<>();
            model.put("userName", order.getCustomer().getName());
            model.put("items", order
                    .getItems());
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            helper.setTo(order.getCustomer().getEmail());
            helper.setText(html, true);
            helper.setSubject("CoffeeShop Invoice");
            helper.setFrom("customerservice@coffeeshop.com");

            emailSender.send(message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CustomException(e.getMessage(), e);
        }
    }
}
