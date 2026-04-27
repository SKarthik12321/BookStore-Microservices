package com.bookstore.notificationservice.dto;

public class OrderEventRequest {
    private Long orderId;
    private String email;
    private String type; // ORDER_PLACED, ORDER_SHIPPED, ORDER_DELIVERED

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
