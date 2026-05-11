package com.example.DoAnCDIO3.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FieldCreateRequest {
    @NotNull(message = "FIELD_TYPE_NOT_NULL")
    Integer field_type_id;

    @NotBlank(message = "FIELD_NAME_NOT_BLANK")
    String name;

    @NotBlank(message = "FIELD_ADDRESS_NOT_BLANK")
    String address;

    String description;

    @NotNull(message = "FIELD_TIME_NOT_NULL")
    LocalTime open_time;

    @NotNull(message = "FIELD_TIME_NOT_NULL")
    LocalTime close_time;
}
