package org.example.accountservice.service;

import lombok.extern.slf4j.Slf4j;
import org.example.accountservice.dto.AccountDto;
import org.example.accountservice.entity.Account;
import org.example.accountservice.mapper.AccountMapper;
import org.example.accountservice.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AccountService {
  private final AccountRepository accountRepository;

  @Autowired
  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public List<AccountDto> getAllAccounts() {
    List<Account> accounts = accountRepository.findAll();

    log.info("Get accounts : {}", accounts);

    return accounts.stream()
            .map(AccountMapper::mapToAccountDto)
            .collect(Collectors.toList());
  }
}
