package org.example.accountservice.dto;

public record CustomerDto(
        String name,
        String email,
        String mobileNumber
) {
}
