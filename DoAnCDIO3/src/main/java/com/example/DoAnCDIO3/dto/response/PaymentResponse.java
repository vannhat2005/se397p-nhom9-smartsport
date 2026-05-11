package com.example.DoAnCDIO3.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse {
    Integer payment_id;
    Integer booking_id;
    String booking_code; // Trả thêm mã đơn đặt sân cho rõ ràng
    String bank_name;
    String account_holder;
    String note;
}
