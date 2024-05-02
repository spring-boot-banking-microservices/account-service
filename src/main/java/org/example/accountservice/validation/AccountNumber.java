package org.example.accountservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = { AccountNumberValidator.class })
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AccountNumber {
  String message() default "Account number must be exactly 10 digits";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
