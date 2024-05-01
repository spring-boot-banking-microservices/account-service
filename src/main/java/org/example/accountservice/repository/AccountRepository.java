package org.example.accountservice.repository;

import jakarta.transaction.Transactional;
import org.example.accountservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
  Optional<Account> findByCustomerId(Long customerId);

  Optional<Account> findByAccountNumber(Long accountNumber);

  @Transactional
  @Modifying
  void deleteByCustomerId(Long customerId);
}
