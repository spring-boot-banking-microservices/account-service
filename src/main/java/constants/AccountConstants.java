package constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountConstants {
  // Account Types
  SAVINGS("Savings"),

  // Address
  ADDRESS("ABC Street, Mumbai"),

  // HTTP Status Codes
  STATUS_201("201"),
  STATUS_200("200"),
  STATUS_500("500"),

  // Success Messages
  MESSAGE_201("Account created successfully"),
  MESSAGE_200("Request processed successfully"),

  // Error Messages
  MESSAGE_500("An error occurred. Please try again later");

  private final String value;
}
