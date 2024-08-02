package com.anuradha.loyaltyprobackend.repository;

import com.anuradha.loyaltyprobackend.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByUuid(String uuid);

    @Query("SELECT c FROM Customer c WHERE c.name LIKE %?1% OR c.email LIKE %?1% OR c.mobile LIKE %?1% OR c.address LIKE %?1%")
    Page<Customer> search(String query, Pageable pageable);
}
