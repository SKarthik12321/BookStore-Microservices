package com.bookstore.customerservice.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity @Table(name="customer_profiles") @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CustomerProfile {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(unique=true,nullable=false) private String userId;
    private String fullName;
    private String phone;
    private String preferences;
}