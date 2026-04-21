package com.bookstore.orderservice.service;
import com.bookstore.orderservice.client.CartServiceClient;
import com.bookstore.orderservice.dto.OrderDto;
import com.bookstore.orderservice.entity.Order;
import com.bookstore.orderservice.entity.OrderItem;
import com.bookstore.orderservice.entity.OrderStatus;
import com.bookstore.orderservice.event.OrderEventPublisher;
import com.bookstore.orderservice.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class OrderService {
    @Autowired private OrderRepository orderRepository;
    @Autowired private CartServiceClient cartServiceClient;
    @Autowired private OrderEventPublisher eventPublisher;

    @SuppressWarnings("unchecked")
    public OrderDto.OrderResponse placeOrder(String userId,String authToken,OrderDto.PlaceOrderRequest req){
        Map<String,Object> cart=cartServiceClient.getCart(authToken);
        List<Map<String,Object>> cartItems=(List<Map<String,Object>>)cart.get("items");
        if(cartItems==null||cartItems.isEmpty()) throw new IllegalArgumentException("Cart is empty");
        Order order=Order.builder().userId(userId).shippingAddress(req.getShippingAddress()).status(OrderStatus.PENDING).build();
        List<OrderItem> items=cartItems.stream().map(i->OrderItem.builder().order(order)
            .productId(Long.valueOf(i.get("productId").toString()))
            .productTitle(i.get("productTitle").toString())
            .quantity(Integer.parseInt(i.get("quantity").toString()))
            .unitPrice(new BigDecimal(i.get("unitPrice").toString())).build()
        ).collect(Collectors.toList());
        order.setItems(items);
        order.setTotalAmount(items.stream().map(OrderItem::getSubtotal).reduce(BigDecimal.ZERO,BigDecimal::add));
        Order saved=orderRepository.save(order);
        try{ cartServiceClient.clearCart(authToken); }catch(Exception ignored){}
        eventPublisher.publishOrderPlaced(saved);
        return toResponse(saved);
    }
    public List<OrderDto.OrderResponse> getUserOrders(String userId){
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId).stream().map(this::toResponse).collect(Collectors.toList());
    }
    public OrderDto.OrderResponse getOrder(Long id,String userId){
        Order o=orderRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Order not found: "+id));
        return toResponse(o);
    }
    public OrderDto.OrderResponse cancelOrder(Long id,String userId){
        Order o=orderRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Order not found: "+id));
        if(!o.getUserId().equals(userId)) throw new IllegalArgumentException("Not your order");
        if(o.getStatus()!=OrderStatus.PENDING) throw new IllegalStateException("Can only cancel PENDING orders");
        o.setStatus(OrderStatus.CANCELLED);
        Order saved=orderRepository.save(o);
        eventPublisher.publishOrderStatusChanged(saved);
        return toResponse(saved);
    }
    public List<OrderDto.OrderResponse> getAllOrders(){
        return orderRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }
    public OrderDto.OrderResponse updateStatus(Long id,OrderDto.UpdateStatusRequest req){
        Order o=orderRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Order not found: "+id));
        o.setStatus(req.getStatus());
        Order saved=orderRepository.save(o);
        eventPublisher.publishOrderStatusChanged(saved);
        return toResponse(saved);
    }
    private OrderDto.OrderResponse toResponse(Order o){
        List<OrderDto.ItemResponse> items=o.getItems().stream().map(i->OrderDto.ItemResponse.builder()
            .productId(i.getProductId()).productTitle(i.getProductTitle()).quantity(i.getQuantity())
            .unitPrice(i.getUnitPrice()).subtotal(i.getSubtotal()).build()).collect(Collectors.toList());
        return OrderDto.OrderResponse.builder().id(o.getId()).userId(o.getUserId()).status(o.getStatus())
            .items(items).totalAmount(o.getTotalAmount()).shippingAddress(o.getShippingAddress()).createdAt(o.getCreatedAt()).build();
    }
}