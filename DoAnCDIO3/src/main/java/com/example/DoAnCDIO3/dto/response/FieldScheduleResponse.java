package com.example.DoAnCDIO3.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FieldScheduleResponse {
    LocalDate schedule_date;
    LocalTime start_time;
    LocalTime end_time;
    Integer status; // 1: Đã được đặt
}
