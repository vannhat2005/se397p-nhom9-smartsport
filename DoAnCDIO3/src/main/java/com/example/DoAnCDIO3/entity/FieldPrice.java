package com.example.DoAnCDIO3.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalTime;


@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "field_prices")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FieldPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "field_id", nullable = false)
    Field field_id;

    @Column(nullable = false)
    Integer day_type;

    @Column(nullable = false)
    LocalTime start_time;

    @Column(nullable = false)
    LocalTime end_time;

    @Column(nullable = false, precision = 12, scale = 2)
    BigDecimal price;

    @Column(nullable = false)
    Integer status;
}
