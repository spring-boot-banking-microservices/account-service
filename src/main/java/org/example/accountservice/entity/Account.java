package org.example.accountservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Account extends Audit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private Long accountNumber;

  @Column(nullable = false, length = 100)
  private String accountType;

  @Column(nullable = false, length = 200)
  private String branchAddress;

  @Column(nullable = false)
  private Long customerId;
}
