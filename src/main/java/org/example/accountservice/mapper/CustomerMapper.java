package org.example.accountservice.mapper;

import org.example.accountservice.dto.CustomerDto;
import org.example.accountservice.entity.Customer;

public class CustomerMapper {
  public static CustomerDto mapToCustomerDto(Customer customer) {
    return new CustomerDto(
            customer.getName(),
            customer.getEmail(),
            customer.getMobileNumber()
    );
  }

  public static Customer mapToCustomer(CustomerDto customerDto) {
    Customer customer = new Customer();

    customer.setName(customerDto.name());
    customer.setEmail(customerDto.email());
    customer.setMobileNumber(customerDto.mobileNumber());

    return customer;
  }
}
