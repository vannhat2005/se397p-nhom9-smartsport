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
public class OwnerBankAccountRequest {
    @NotNull(message = "BANK_OWNER_NOT_NULL")
    Integer user_id;

    @NotBlank(message = "BANK_NAME_NOT_BLANK")
    String bank_name;

    @NotBlank(message = "BANK_NUMBER_NOT_BLANK")
    String account_number;

    @NotBlank(message = "BANK_HOLDER_NOT_BLANK")
    String account_holder;
}
