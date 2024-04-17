package org.example.accountservice.controller;

import org.example.accountservice.dto.CustomerDto;
import org.example.accountservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/customers", produces = { MediaType.APPLICATION_JSON_VALUE })
public class CustomerController {
  private final CustomerService customerService;

  @Autowired
  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping
  public ResponseEntity<List<CustomerDto>> getAllAccounts() {
    return ResponseEntity.ok(customerService.getAllCustomers());
  }
}
