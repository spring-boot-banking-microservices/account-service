package org.example.accountservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Customer Management", description = "APIs for managing customer operations")
public class CustomerController {
  private final CustomerService customerService;

  @Autowired
  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @Operation(
          summary = "Get all customers",
          description = "Retrieve a list of all customers in the system."
  )
  @GetMapping
  public ResponseEntity<List<CustomerDto>> getAllAccounts() {
    return ResponseEntity.ok(customerService.getAllCustomers());
  }
}
