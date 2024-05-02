package org.example.accountservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.accountservice.validation.AccountNumber;

public record AccountDto(
        @NotNull(message = "Account number is required")
        @AccountNumber(message = "Account number must be exactly 10 digits")
        Long accountNumber,

        @NotBlank(message = "Account type is required")
        @Size(max = 100, message = "Account type cannot exceed 100 characters")
        String accountType,

        @NotBlank(message = "Branch address is required")
        @Size(max = 200, message = "Branch address cannot exceed 200 characters")
        String branchAddress
) {
}
