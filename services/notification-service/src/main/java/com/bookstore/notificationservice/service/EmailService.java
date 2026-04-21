package com.bookstore.notificationservice.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.Map;
@Service
public class EmailService {
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    @Autowired(required=false) private JavaMailSender mailSender;
    public void sendOrderConfirmation(Map<String,Object> event){
        log.info("ORDER_PLACED event received: orderId={}", event.get("orderId"));
        send((String)event.getOrDefault("email",""), "Order Confirmed #"+event.get("orderId"), "Your order has been placed successfully!");
    }
    public void sendShippingUpdate(Map<String,Object> event){
        log.info("ORDER_SHIPPED event: orderId={}", event.get("orderId"));
        send((String)event.getOrDefault("email",""), "Order Shipped #"+event.get("orderId"), "Your order is on its way!");
    }
    public void sendDeliveryConfirmation(Map<String,Object> event){
        log.info("ORDER_DELIVERED event: orderId={}", event.get("orderId"));
        send((String)event.getOrDefault("email",""), "Order Delivered #"+event.get("orderId"), "Your order has been delivered!");
    }
    public void sendWelcomeEmail(Map<String,Object> event){
        log.info("USER_REGISTERED event: email={}", event.get("email"));
        send((String)event.getOrDefault("email",""), "Welcome to Bookstore!", "Thank you for registering!");
    }
    private void send(String to,String subject,String body){
        if(mailSender==null||to==null||to.isEmpty()){ log.info("[EMAIL-STUB] To:{} Subject:{} Body:{}", to,subject,body); return; }
        try{
            SimpleMailMessage msg=new SimpleMailMessage();
            msg.setTo(to); msg.setSubject(subject); msg.setText(body);
            mailSender.send(msg);
        }catch(Exception e){ log.error("Failed to send email: {}",e.getMessage()); }
    }
}