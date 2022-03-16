package com.themusicshop.msgqueue.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class DiscountPublisher {

    private final JmsTemplate jmsTemplate;

    @Autowired
    public DiscountPublisher(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(Long discountId) {
        jmsTemplate.convertAndSend("discount-queue", discountId);
    }
}
