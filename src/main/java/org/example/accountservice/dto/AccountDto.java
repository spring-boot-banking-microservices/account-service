package org.example.accountservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.accountservice.validation.AccountNumber;

@Schema(
        name = "Account",
        description = "Represents account details"
)
public record AccountDto(
        @Schema(description = "Account number", example = "1234567890")
        @NotNull(message = "Account number is required")
        @AccountNumber(message = "Account number must be exactly 10 digits")
        Long accountNumber,

        @Schema(description = "Account type", example = "Savings")
        @NotBlank(message = "Account type is required")
        @Size(max = 100, message = "Account type cannot exceed 100 characters")
        String accountType,

        @Schema(description = "Branch address", example = "123 Main St, Anytown USA")
        @NotBlank(message = "Branch address is required")
        @Size(max = 200, message = "Branch address cannot exceed 200 characters")
        String branchAddress
) {
}
