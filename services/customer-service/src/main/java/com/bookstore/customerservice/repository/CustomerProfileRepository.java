package com.bookstore.customerservice.repository;
import com.bookstore.customerservice.entity.CustomerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface CustomerProfileRepository extends JpaRepository<CustomerProfile,Long> {
    Optional<CustomerProfile> findByUserId(String userId);
}