package com.example.DoAnCDIO3.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bookings")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer booking_id;

    @Column(nullable = false, length = 20)
    String booking_code;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user_id;

    @ManyToOne
    @JoinColumn(name = "field_id", nullable = false)
    Field field_id;

    @Column(nullable = false)
    LocalDate booking_date;

    @Column(nullable = false)
    LocalTime start_time;

    @Column(nullable = false)
    LocalTime end_time;

    @Column(nullable = false, precision = 12, scale = 2)
    BigDecimal total_amount;

    @Column(nullable = false)
    Integer status;

    @Column(length = 255)
    String note;

    @Column(nullable = false, updatable = false)
    LocalDateTime created_at;

    LocalDateTime updated_at;
}
