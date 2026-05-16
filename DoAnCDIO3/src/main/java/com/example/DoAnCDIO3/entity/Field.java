package com.example.DoAnCDIO3.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fields")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user_id;

    @ManyToOne
    @JoinColumn(name = "field_type_id", nullable = false)
    FieldType field_type_id;

    @Column(nullable = false, length = 150)
    String name;

    @Column(nullable = false, length = 255)
    String address;

    @Column(length = 500)
    String description;

    @Column(nullable = false)
    LocalTime open_time;

    @Column(nullable = false)
    LocalTime close_time;


    @Column(name = "image", length = 1000)
    String image;

    @Column(nullable = false)
    Integer status;

    @Column(nullable = false, updatable = false)
    LocalDateTime created_at;

    LocalDateTime updated_at;
}
