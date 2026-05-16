package com.example.DoAnCDIO3.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Integer id;
    String role_name; // Trả về tên role thay vì id cho FE dễ hiển thị
    String role_id;
    String full_name;
    String email;
    String phone;
    String avatar;
    String address;
    Integer status;
    LocalDateTime created_at;
}
