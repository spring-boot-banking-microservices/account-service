package org.example.accountservice.controller;

import org.example.accountservice.dto.AccountDto;
import org.example.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/accounts", produces = { MediaType.APPLICATION_JSON_VALUE })
public class AccountController {
  private final AccountService accountService;

  @Autowired
  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping
  public ResponseEntity<List<AccountDto>> getAllAccounts() {
    return ResponseEntity.ok(accountService.getAllAccounts());
  }
}
