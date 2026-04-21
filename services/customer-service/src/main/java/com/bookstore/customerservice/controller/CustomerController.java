package com.bookstore.customerservice.controller;
import com.bookstore.customerservice.dto.CustomerDto;
import com.bookstore.customerservice.service.CustomerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/customers") @Tag(name="Customer Details")
public class CustomerController {
    @Autowired private CustomerService customerService;
    @GetMapping("/details") public ResponseEntity<CustomerDto.ProfileResponse> get(Authentication auth){ return ResponseEntity.ok(customerService.getProfile(auth.getName())); }
    @PostMapping("/details") public ResponseEntity<CustomerDto.ProfileResponse> create(Authentication auth,@RequestBody CustomerDto.ProfileRequest req){ return ResponseEntity.status(201).body(customerService.createProfile(auth.getName(),req)); }
    @PutMapping("/details") public ResponseEntity<CustomerDto.ProfileResponse> update(Authentication auth,@RequestBody CustomerDto.ProfileRequest req){ return ResponseEntity.ok(customerService.updateProfile(auth.getName(),req)); }
    @GetMapping("/addresses") public ResponseEntity<List<CustomerDto.AddressResponse>> addresses(Authentication auth){ return ResponseEntity.ok(customerService.getAddresses(auth.getName())); }
    @PostMapping("/addresses") public ResponseEntity<CustomerDto.AddressResponse> addAddress(Authentication auth,@RequestBody CustomerDto.AddressRequest req){ return ResponseEntity.status(201).body(customerService.addAddress(auth.getName(),req)); }
    @DeleteMapping("/addresses/{id}") public ResponseEntity<Void> deleteAddress(@PathVariable Long id){ customerService.deleteAddress(id); return ResponseEntity.noContent().build(); }
    @PutMapping("/addresses/{id}/default") public ResponseEntity<CustomerDto.AddressResponse> setDefault(Authentication auth,@PathVariable Long id){ return ResponseEntity.ok(customerService.setDefault(id,auth.getName())); }
}