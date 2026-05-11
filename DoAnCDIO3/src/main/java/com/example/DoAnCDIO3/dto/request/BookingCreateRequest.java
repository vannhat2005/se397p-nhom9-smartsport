package com.example.DoAnCDIO3.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingCreateRequest {
    @NotNull(message = "BOOKING_FIELD_NOT_NULL")
    Integer field_id;

    @NotNull(message = "BOOKING_DATE_NOT_NULL")
    @FutureOrPresent(message = "BOOKING_DATE_PAST")
    LocalDate booking_date;

    @NotNull(message = "BOOKING_TIME_NOT_NULL")
    LocalTime start_time;

    @NotNull(message = "BOOKING_TIME_NOT_NULL")
    LocalTime end_time;

    String note;
}
