package com.example.DoAnCDIO3.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FieldPriceRequest {
    @NotNull(message = "PRICE_FIELD_NOT_NULL")
    Integer field_id;

    @NotNull(message = "PRICE_DAY_TYPE_NOT_NULL")
    Integer day_type;

    @NotNull
    LocalTime start_time;

    @NotNull
    LocalTime end_time;

    @NotNull(message = "PRICE_NOT_NULL")
    @DecimalMin(value = "0.0", inclusive = false, message = "PRICE_MIN_INVALID")
    BigDecimal price;

    @NotNull
    Integer status;
}
