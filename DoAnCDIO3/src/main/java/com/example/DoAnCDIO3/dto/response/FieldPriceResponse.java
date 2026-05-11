package com.example.DoAnCDIO3.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FieldPriceResponse {
    Integer id;
    Integer field_id;
    String field_name; // Bổ sung thêm tên sân để FE dễ hiển thị
    Integer day_type;
    LocalTime start_time;
    LocalTime end_time;
    BigDecimal price;
    Integer status;
}
