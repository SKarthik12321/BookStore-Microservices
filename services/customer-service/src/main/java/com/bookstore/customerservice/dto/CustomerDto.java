package com.bookstore.customerservice.dto;
import lombok.*;
public class CustomerDto {
    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class ProfileRequest { private String fullName,phone,preferences; }
    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class ProfileResponse { private Long id; private String userId,fullName,phone,preferences; }
    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class AddressRequest { private String street,city,state,zipCode,country; private boolean isDefault; }
    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class AddressResponse { private Long id; private String street,city,state,zipCode,country; private boolean isDefault; }
}