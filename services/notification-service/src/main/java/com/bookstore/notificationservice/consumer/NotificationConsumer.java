package com.bookstore.notificationservice.consumer;
import com.bookstore.notificationservice.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.Map;
@Service
public class NotificationConsumer {
    private static final Logger log = LoggerFactory.getLogger(NotificationConsumer.class);
    @Autowired private EmailService emailService;
    @KafkaListener(topics="order-events",groupId="notification-group")
    public void onOrderEvent(Map<String,Object> event){
        log.info("Received order event: {}", event);
        String type=(String)event.getOrDefault("type","");
        switch(type){
            case "ORDER_PLACED"    -> emailService.sendOrderConfirmation(event);
            case "ORDER_SHIPPED"   -> emailService.sendShippingUpdate(event);
            case "ORDER_DELIVERED" -> emailService.sendDeliveryConfirmation(event);
            default -> log.info("Unhandled order event type: {}", type);
        }
    }
    @KafkaListener(topics="user-events",groupId="notification-group")
    public void onUserEvent(Map<String,Object> event){
        log.info("Received user event: {}", event);
        if("USER_REGISTERED".equals(event.get("type"))) emailService.sendWelcomeEmail(event);
    }
}