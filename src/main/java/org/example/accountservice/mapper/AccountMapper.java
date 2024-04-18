package org.example.accountservice.mapper;

import org.example.accountservice.dto.AccountDto;
import org.example.accountservice.entity.Account;

public class AccountMapper {
  public static AccountDto mapToAccountDto(Account account) {
    return new AccountDto(
            account.getAccountNumber(),
            account.getAccountType(),
            account.getBranchAddress()
    );
  }

  public static Account mapToAccount(AccountDto accountDto) {
    Account account = new Account();

    account.setAccountNumber(accountDto.accountNumber());
    account.setAccountType(accountDto.accountType());
    account.setBranchAddress(accountDto.branchAddress());

    return account;
  }
}
