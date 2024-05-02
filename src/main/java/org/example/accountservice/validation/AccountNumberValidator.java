package org.example.accountservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AccountNumberValidator implements ConstraintValidator<AccountNumber, Long> {
  @Override
  public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {
    return aLong != null && String.valueOf(aLong).length() == 10;
  }
}
