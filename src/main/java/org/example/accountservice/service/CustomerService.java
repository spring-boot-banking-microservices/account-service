package org.example.accountservice.service;

import lombok.extern.slf4j.Slf4j;
import org.example.accountservice.dto.CustomerDto;
import org.example.accountservice.entity.Customer;
import org.example.accountservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerService {
  private final CustomerRepository customerRepository;

  @Autowired
  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public List<CustomerDto> getAllCustomers() {
    List<Customer> customers = customerRepository.findAll();

    log.info("Get customers : {}", customers);

    return customers.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
  }

  private CustomerDto convertToDto(Customer customer) {
    return new CustomerDto(
            customer.getName(),
            customer.getEmail(),
            customer.getMobileNumber()
    );
  }
}
