package org.example.accountservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Schema(
        name = "AccountCustomer",
        description = "Represents account and customer details"
)
public record AccountCustomerDto(
        @Schema(description = "Account details")
        @NotNull(message = "Account details are required")
        @Valid
        AccountDto accountDto,

        @Schema(description = "Customer details")
        @NotNull(message = "Customer details are required")
        @Valid
        CustomerDto customerDto
) {
}
