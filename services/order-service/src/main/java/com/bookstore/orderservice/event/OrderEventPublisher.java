package com.bookstore.orderservice.event;
import com.bookstore.orderservice.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Map;
@Service
public class OrderEventPublisher {
    @Autowired private KafkaTemplate<String,Object> kafkaTemplate;
    public void publishOrderPlaced(Order order){
        try{ kafkaTemplate.send("order-events",Map.of("type","ORDER_PLACED","orderId",order.getId(),"userId",order.getUserId(),"timestamp",LocalDateTime.now().toString())); }catch(Exception ignored){}
    }
    public void publishOrderStatusChanged(Order order){
        try{ kafkaTemplate.send("order-events",Map.of("type","ORDER_"+order.getStatus(),"orderId",order.getId(),"userId",order.getUserId(),"status",order.getStatus().name(),"timestamp",LocalDateTime.now().toString())); }catch(Exception ignored){}
    }
}