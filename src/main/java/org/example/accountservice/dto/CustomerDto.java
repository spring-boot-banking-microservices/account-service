package org.example.accountservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(
        name = "Customer",
        description = "Represents customer details"
)
public record CustomerDto(
        @Schema(description = "Customer name", example = "John Doe")
        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name cannot exceed 100 characters")
        String name,

        @Schema(description = "Customer email", example = "john.doe@example.com")
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        @Size(max = 100, message = "Email cannot exceed 100 characters")
        String email,

        @Schema(description = "Customer mobile number", example = "1234567890")
        @NotBlank(message = "Mobile number is required")
        @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be exactly 10 digits")
        String mobileNumber
) {
}
