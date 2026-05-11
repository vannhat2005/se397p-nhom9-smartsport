package com.example.DoAnCDIO3.dto.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @NotBlank(message = "FULLNAME_NOT_BLANK")
    String full_name;

    @Pattern(regexp = "^\\d{10}$", message = "PHONE_INVALID")
    String phone;

    String avatar;
    String address;

    Integer status; // Có thể dùng để khóa/mở khóa tài khoản
}
