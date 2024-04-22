package org.example.accountservice.service;

import lombok.extern.slf4j.Slf4j;
import org.example.accountservice.dto.CustomerDto;
import org.example.accountservice.entity.Customer;
import org.example.accountservice.mapper.CustomerMapper;
import org.example.accountservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
            .map(CustomerMapper::mapToCustomerDto)
            .toList();
  }
}
