package org.example.accountservice.service;

import lombok.extern.slf4j.Slf4j;
import org.example.accountservice.constants.Constants;
import org.example.accountservice.dto.AccountCustomerDto;
import org.example.accountservice.dto.AccountDto;
import org.example.accountservice.dto.CustomerDto;
import org.example.accountservice.entity.Account;
import org.example.accountservice.entity.Customer;
import org.example.accountservice.exception.CustomerAlreadyExistsException;
import org.example.accountservice.exception.ResourceNotFoundException;
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

    Account account = createAccountForCustomer(customer.getId());

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
      throw new CustomerAlreadyExistsException(Constants.CUSTOMER.getValue(), "mobileNumber", mobileNumber);
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
   * @param customerId The Customer id for which the account is being created.
   * @return The newly created Account entity.
   */
  private Account createAccountForCustomer(Long customerId) {
    Long accountNumber = generateRandomAccountNumber();

    return buildAccount(accountNumber, customerId);
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
   * @param customerId      The Customer id to associate with the Account.
   * @return The built Account entity.
   */
  private Account buildAccount(Long accountNumber, Long customerId) {
    Account account = new Account();

    account.setAccountNumber(accountNumber);
    account.setAccountType(Constants.SAVINGS.getValue());
    account.setBranchAddress(Constants.ADDRESS.getValue());
    account.setCustomerId(customerId);

    return account;
  }

  /**
   * Fetches the account and customer details based on the provided mobile number.
   *
   * @param mobileNumber The mobile number associated with the customer.
   * @return AccountCustomerDto object containing the account and customer details.
   */
  public AccountCustomerDto fetchAccountAndCustomer(String mobileNumber) {
    Customer customer = getCustomerByMobileNumber(mobileNumber);

    Account account = getAccountByCustomerId(customer.getId());

    return new AccountCustomerDto(
            AccountMapper.mapToAccountDto(account),
            CustomerMapper.mapToCustomerDto(customer)
    );
  }

  /**
   * Retrieves the customer entity associated with the provided mobile number.
   *
   * @param mobileNumber The mobile number associated with the customer.
   * @return Customer entity associated with the provided mobile number.
   * @throws ResourceNotFoundException if the customer is not found for the given mobile number.
   */
  private Customer getCustomerByMobileNumber(String mobileNumber) {
    return customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() ->
            new ResourceNotFoundException(Constants.CUSTOMER.getValue(), "mobileNumber", mobileNumber)
    );
  }

  /**
   * Retrieves the account entity associated with the provided customer ID.
   *
   * @param customerId The ID of the customer associated with the account.
   * @return Account entity associated with the provided customer ID.
   * @throws ResourceNotFoundException if the account is not found for the given customer ID.
   */
  private Account getAccountByCustomerId(Long customerId) {
    return accountRepository.findByCustomerId(customerId).orElseThrow(() ->
            new ResourceNotFoundException(Constants.ACCOUNT.getValue(), "customerId", customerId.toString())
    );
  }

  /**
   * Updates the account and customer details based on the provided AccountCustomerDto.
   *
   * @param accountCustomerDto The DTO containing the account and customer details to be updated.
   * @return true if the update operation is successful, false otherwise.
   */
  public boolean updateAccountAndCustomer(AccountCustomerDto accountCustomerDto) {
    boolean isUpdated = false;

    AccountDto accountDto = accountCustomerDto.accountDto();
    CustomerDto customerDto = accountCustomerDto.customerDto();

    if (accountDto != null && customerDto != null) {
      Long accountNumber = accountDto.accountNumber();
      Account account = getAccountByAccountNumber(accountNumber);
      Customer customer = getCustomerById(account.getCustomerId());

      Account updatedAccount = AccountMapper.mapToAccount(accountDto);
      Customer updatedCustomer = CustomerMapper.mapToCustomer(customerDto);

      updatedCustomer.setId(customer.getId());
      updatedAccount.setId(account.getId());
      updatedAccount.setCustomerId(customer.getId());

      customerRepository.save(updatedCustomer);
      accountRepository.save(updatedAccount);

      isUpdated = true;
    }

    return isUpdated;
  }

  /**
   * Retrieves the customer entity associated with the provided customer ID.
   *
   * @param customerId The ID of the customer to retrieve.
   * @return Customer entity associated with the provided customer ID.
   * @throws ResourceNotFoundException if the customer is not found for the given customer ID.
   */
  private Customer getCustomerById(Long customerId) {
    return customerRepository.findById(customerId).orElseThrow(() ->
            new ResourceNotFoundException(Constants.CUSTOMER.getValue(), "customerId", customerId.toString())
    );
  }

  /**
   * Retrieves the account entity associated with the provided account number.
   *
   * @param accountNumber The account number to retrieve the account for.
   * @return Account entity associated with the provided account number.
   * @throws ResourceNotFoundException if the account is not found for the given account number.
   */
  private Account getAccountByAccountNumber(Long accountNumber) {
    return accountRepository.findByAccountNumber(accountNumber).orElseThrow(() ->
            new ResourceNotFoundException(Constants.ACCOUNT.getValue(), "accountNumber", accountNumber.toString())
    );
  }

  /**
   * Deletes the account and customer associated with the provided mobile number.
   *
   * @param mobileNumber The mobile number of the customer whose account and details need to be deleted.
   * @return true if the deletion operation is successful, false otherwise.
   * @throws ResourceNotFoundException if the customer associated with the provided mobile number is not found.
   */
  public boolean deleteAccountAndCustomer(String mobileNumber) {
    Customer customer = getCustomerByMobileNumber(mobileNumber);

    Long customerId = customer.getId();
    accountRepository.deleteByCustomerId(customerId);
    customerRepository.deleteById(customerId);

    return true;
  }
}
