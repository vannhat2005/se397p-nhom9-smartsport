package com.example.DoAnCDIO3.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "owner_bank_accounts")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OwnerBankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user_id;

    @Column(length = 100)
    String bank_name;

    @Column(length = 30)
    String account_number;

    @Column(length = 100)
    String account_holder;
}
