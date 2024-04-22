package org.example.accountservice.mapper;

import org.example.accountservice.dto.AccountDto;
import org.example.accountservice.entity.Account;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountMapperTest {
  @Test
  void testMapToAccountDto() {
    // Given
    Account account = new Account();
    account.setAccountNumber(10000000L);
    account.setAccountType("Savings");
    account.setBranchAddress("123 Main St, City");

    // When
    AccountDto accountDto = AccountMapper.mapToAccountDto(account);

    // Then
    assertEquals(10000000L, accountDto.accountNumber());
    assertEquals("Savings", accountDto.accountType());
    assertEquals("123 Main St, City", accountDto.branchAddress());
  }

  @Test
  void testMapToAccount() {
    // Given
    AccountDto accountDto = new AccountDto(10000000L, "Savings", "123 Main St, City");

    // When
    Account account = AccountMapper.mapToAccount(accountDto);

    // Then
    assertEquals(10000000L, account.getAccountNumber());
    assertEquals("Savings", account.getAccountType());
    assertEquals("123 Main St, City", account.getBranchAddress());
  }
}
