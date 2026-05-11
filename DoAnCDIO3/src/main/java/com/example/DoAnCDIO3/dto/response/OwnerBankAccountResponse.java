package com.example.DoAnCDIO3.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OwnerBankAccountResponse {
    Integer id;
    Integer user_id;
    String owner_name; // Trả thêm tên chủ sân
    String bank_name;
    String account_number;
    String account_holder;
}
