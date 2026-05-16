package com.example.DoAnCDIO3.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingResponse {
    Integer booking_id;
    String booking_code;
    String customer_name;
    String field_name;
    LocalDate booking_date;
    LocalTime start_time;
    LocalTime end_time;
    BigDecimal total_amount;
    Integer status;
    String note;
}
