package org.example.accountservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CustomerDto(
        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name cannot exceed 100 characters")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        @Size(max = 100, message = "Email cannot exceed 100 characters")
        String email,

        @NotBlank(message = "Mobile number is required")
        @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be exactly 10 digits")
        String mobileNumber
) {
}
