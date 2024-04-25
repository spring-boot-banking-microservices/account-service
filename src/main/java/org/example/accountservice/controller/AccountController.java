package org.example.accountservice.controller;

import org.example.accountservice.constants.AccountConstants;
import org.example.accountservice.dto.AccountCustomerDto;
import org.example.accountservice.dto.AccountDto;
import org.example.accountservice.dto.CustomerDto;
import org.example.accountservice.dto.ResponseDto;
import org.example.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
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

  /**
   * Endpoint to create a new account for a customer.
   *
   * @param customerDto The DTO containing the customer's details to create the account.
   * @return ResponseEntity containing the HTTP status and response message upon successful account creation.
   */
  @PostMapping("/create")
  public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto) {
    accountService.createAccount(customerDto);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ResponseDto(
                    AccountConstants.STATUS_201.getValue(),
                    AccountConstants.MESSAGE_201.getValue())
            );
  }

  /**
   * Fetches the account and customer details based on the provided mobile number.
   *
   * @param mobileNumber The mobile number associated with the customer.
   * @return ResponseEntity containing the AccountCustomerDto object with the account and customer details.
   */
  @GetMapping("/fetch")
  public ResponseEntity<AccountCustomerDto> fetchAccountAndCustomer(@RequestParam String mobileNumber) {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(accountService.fetchAccountAndCustomer(mobileNumber));
  }
}
