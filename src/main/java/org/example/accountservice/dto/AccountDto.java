package org.example.accountservice.dto;

public record AccountDto(
        Long accountNumber,
        String accountType,
        String branchAddress
) {
}
