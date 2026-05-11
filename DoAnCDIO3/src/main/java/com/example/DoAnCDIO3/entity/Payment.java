package com.example.DoAnCDIO3.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer payment_id;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    Booking booking_id;

    @Column(length = 100)
    String bank_name;

    @Column(length = 100)
    String account_holder;

    @Column(length = 255)
    String note;
}
