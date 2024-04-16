package org.example.accountservice.dto;

public record AccountDto(
        String accountNumber,
        String accountType,
        String branchAddress
) {
}
