package com.example.DoAnCDIO3.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "field_schedules")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FieldSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "field_id", nullable = false)
    Field field_id;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    Booking booking_id;

    @Column(nullable = false)
    LocalDate schedule_date;

    @Column(nullable = false)
    LocalTime start_time;

    @Column(nullable = false)
    LocalTime end_time;

    @Column(nullable = false)
    Integer status;

    @Column(nullable = false, updatable = false)
    LocalDateTime created_at;

    LocalDateTime updated_at;
}
