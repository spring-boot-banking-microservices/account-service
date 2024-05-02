package org.example.accountservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record AccountCustomerDto(
        @NotNull(message = "Account details are required")
        @Valid
        AccountDto accountDto,

        @NotNull(message = "Customer details are required")
        @Valid
        CustomerDto customerDto
) {
}
