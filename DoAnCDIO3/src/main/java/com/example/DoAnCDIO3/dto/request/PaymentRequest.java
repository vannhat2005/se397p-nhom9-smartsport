package com.example.DoAnCDIO3.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentRequest {
    @NotNull(message = "PAYMENT_BOOKING_NOT_NULL")
    Integer booking_id;

    @NotBlank(message = "PAYMENT_BANK_NOT_BLANK")
    String bank_name;

    @NotBlank(message = "PAYMENT_ACCOUNT_NOT_BLANK")
    String account_holder;

    String note;
}
