package org.example.accountservice.mapper;

import org.example.accountservice.dto.CustomerDto;
import org.example.accountservice.entity.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerMapperTest {
  @Test
  void testMapToCustomerDto() {
    // Given
    Customer customer = new Customer();
    customer.setName("John Doe");
    customer.setEmail("john.doe@example.com");
    customer.setMobileNumber("1234567890");

    // When

    CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer);

    // Then
    assertEquals("John Doe", customerDto.name());
    assertEquals("john.doe@example.com", customerDto.email());
    assertEquals("1234567890", customerDto.mobileNumber());
  }

  @Test
  void testMapToCustomer() {
    // Given
    CustomerDto customerDto = new CustomerDto("Jane Smith", "jane.smith@example.com", "9876543210");

    // When
    Customer customer = CustomerMapper.mapToCustomer(customerDto);

    // Then
    assertEquals("Jane Smith", customer.getName());
    assertEquals("jane.smith@example.com", customer.getEmail());
    assertEquals("9876543210", customer.getMobileNumber());
  }
}
