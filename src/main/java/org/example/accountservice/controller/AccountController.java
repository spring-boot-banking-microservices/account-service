package org.example.accountservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.example.accountservice.constants.Constants;
import org.example.accountservice.dto.*;
import org.example.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@Tag(name = "Account Management", description = "APIs for managing customer accounts")
public class AccountController {
  private final AccountService accountService;

  @Autowired
  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @Operation(
          summary = "Get all accounts",
          description = "Retrieve a list of all accounts in the system."
  )
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
  @Operation(
          summary = "Create a new account",
          description = "Create a new account for a customer."
  )
  @PostMapping("/create")
  public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
    accountService.createAccount(customerDto);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ResponseDto(
                    Constants.STATUS_201.getValue(),
                    Constants.MESSAGE_201.getValue())
            );
  }

  /**
   * Fetches the account and customer details based on the provided mobile number.
   *
   * @param mobileNumber The mobile number associated with the customer.
   * @return ResponseEntity containing the AccountCustomerDto object with the account and customer details.
   */
  @Operation(
          summary = "Fetch account and customer details",
          description = "Retrieve the account and customer details based on the provided mobile number."
  )
  @GetMapping("/fetch")
  public ResponseEntity<AccountCustomerDto> fetchAccountAndCustomer(
          @RequestParam
          @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be exactly 10 digits")
          String mobileNumber
  ) {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(accountService.fetchAccountAndCustomer(mobileNumber));
  }

  /**
   * Endpoint to update both the account and customer details.
   *
   * @param accountCustomerDto The DTO containing the account and customer details to be updated.
   * @return ResponseEntity containing the HTTP status and response message upon successful or failed update.
   */
  @Operation(summary = "Update account and customer details", description = "Update both the account and customer details.")
  @ApiResponse(
          responseCode = "200",
          description = "Successful update",
          content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ResponseDto.class)
          )
  )
  @ApiResponse(
          responseCode = "500",
          description = "Internal server error",
          content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDto.class)
          )
  )
  @PutMapping("/update")
  public ResponseEntity<ResponseDto> updateAccountAndCustomer(@Valid @RequestBody AccountCustomerDto accountCustomerDto) {
    boolean isUpdated = accountService.updateAccountAndCustomer(accountCustomerDto);

    if (isUpdated) {
      return ResponseEntity
              .status(HttpStatus.OK)
              .body(new ResponseDto(
                      Constants.STATUS_200.getValue(),
                      Constants.MESSAGE_200.getValue())
              );
    } else {
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(new ResponseDto(
                      Constants.STATUS_500.getValue(),
                      Constants.MESSAGE_500.getValue())
              );
    }
  }

  /**
   * Deletes the account and customer associated with the provided mobile number.
   *
   * @param mobileNumber The mobile number of the customer whose account and details need to be deleted.
   * @return A ResponseEntity containing the response status and a message indicating the result of the deletion operation.
   */
  @Operation(
          summary = "Delete account and customer",
          description = "Deletes the account and customer associated with the provided mobile number."
  )
  @ApiResponse(
          responseCode = "200",
          description = "Successful response",
          content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ResponseDto.class)
          )
  )
  @ApiResponse(
          responseCode = "500",
          description = "Internal Server Error",
          content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDto.class)
          )
  )
  @DeleteMapping("/delete")
  public ResponseEntity<ResponseDto> deleteAccountAndCustomer(
          @RequestParam
          @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be exactly 10 digits")
          String mobileNumber
  ) {
    boolean isDeleted = accountService.deleteAccountAndCustomer(mobileNumber);

    if (isDeleted) {
      return ResponseEntity
              .status(HttpStatus.OK)
              .body(new ResponseDto(
                      Constants.STATUS_200.getValue(),
                      Constants.MESSAGE_200.getValue())
              );
    } else {
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(new ResponseDto(
                      Constants.STATUS_500.getValue(),
                      Constants.MESSAGE_500.getValue())
              );
    }
  }
}
