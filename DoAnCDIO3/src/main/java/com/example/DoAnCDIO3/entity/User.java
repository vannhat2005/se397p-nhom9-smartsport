package com.example.DoAnCDIO3.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;


@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    Role role_id;

    @Column(nullable = false, length = 100)
    String full_name;

    @Column(nullable = false, length = 100, unique = true)
    String email;

    @Column(length = 10)
    String phone;

    @Column(nullable = false, length = 255)
    String password;

    @Column(length = 255)
    String avatar;

    @Column(length = 255)
    String address;

    @Column(nullable = false)
    Integer status;

    @Column(nullable = false, updatable = false)
    LocalDateTime created_at;

    LocalDateTime updated_at;
}
