package org.example.accountservice.repository;

import org.example.accountservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
  Optional<Customer> findByMobileNumber(String mobileNumber);
}
