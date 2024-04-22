package org.example.accountservice.service;

import lombok.extern.slf4j.Slf4j;
import org.example.accountservice.constants.AccountConstants;
import org.example.accountservice.dto.AccountDto;
import org.example.accountservice.dto.CustomerDto;
import org.example.accountservice.entity.Account;
import org.example.accountservice.entity.Customer;
import org.example.accountservice.exception.CustomerAlreadyExistsException;
import org.example.accountservice.mapper.AccountMapper;
import org.example.accountservice.mapper.CustomerMapper;
import org.example.accountservice.repository.AccountRepository;
import org.example.accountservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class AccountService {
  private final Random random = new Random();
  private final AccountRepository accountRepository;
  private final CustomerRepository customerRepository;

  @Autowired
  public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository) {
    this.accountRepository = accountRepository;
    this.customerRepository = customerRepository;
  }

  public List<AccountDto> getAllAccounts() {
    List<Account> accounts = accountRepository.findAll();

    log.info("Get accounts : {}", accounts);

    return accounts.stream()
            .map(AccountMapper::mapToAccountDto)
            .toList();
  }

  /**
   * Creates a new account for the given customer.
   *
   * @param customerDto The customer data transfer object containing the customer's information.
   *                    The object should contain the customer's name, email, and mobile number.
   * @throws CustomerAlreadyExistsException If a customer already exists with the provided mobile number.
   */
  public void createAccount(CustomerDto customerDto) {
    validateCustomerDoesNotExist(customerDto.mobileNumber());

    Customer customer = mapAndSaveCustomer(customerDto);

    Account account = createAccountForCustomer(customer);

    accountRepository.save(account);
  }

  /**
   * Validates if a customer with the given mobile number already exists in the system.
   *
   * @param mobileNumber The mobile number to check for an existing customer.
   * @throws CustomerAlreadyExistsException If a customer already exists with the provided mobile number.
   */
  private void validateCustomerDoesNotExist(String mobileNumber) {
    if (customerRepository.findByMobileNumber(mobileNumber).isPresent()) {
      throw new CustomerAlreadyExistsException("Customer", "mobileNumber", mobileNumber);
    }
  }

  /**
   * Creates a new Customer entity from the provided CustomerDto and saves it.
   *
   * @param customerDto The DTO representing the customer's details.
   * @return The newly created Customer entity.
   */
  private Customer mapAndSaveCustomer(CustomerDto customerDto) {
    Customer customer = CustomerMapper.mapToCustomer(customerDto);

    return customerRepository.save(customer);
  }

  /**
   * Creates a new Account entity for the given Customer.
   * Generates a random account number and sets other account details.
   *
   * @param customer The Customer entity for which the account is being created.
   * @return The newly created Account entity.
   */
  private Account createAccountForCustomer(Customer customer) {
    Long accountNumber = generateRandomAccountNumber();

    return buildAccount(accountNumber, customer);
  }

  /**
   * Generates a random account number.
   *
   * @return The randomly generated account number.
   */
  private Long generateRandomAccountNumber() {
    return 1000000000L + random.nextInt(900000000);
  }

  /**
   * Builds an Account entity with the given account number and associated Customer.
   *
   * @param accountNumber The account number to set for the Account entity.
   * @param customer      The Customer entity to associate with the Account.
   * @return The built Account entity.
   */
  private Account buildAccount(Long accountNumber, Customer customer) {
    Account account = new Account();

    account.setAccountNumber(accountNumber);
    account.setAccountType(AccountConstants.SAVINGS.getValue());
    account.setBranchAddress(AccountConstants.ADDRESS.getValue());
    account.setCustomerId(customer.getId());

    return account;
  }
}
