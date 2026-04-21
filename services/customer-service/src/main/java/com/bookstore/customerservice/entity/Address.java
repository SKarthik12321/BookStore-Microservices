package com.bookstore.customerservice.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity @Table(name="addresses") @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Address {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String userId;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private boolean isDefault;
}