package com.example.DoAnCDIO3.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FieldResponse {
    Integer id;
    String owner_name;
    String field_type_name;
    String name;
    String address;
    String description;
    LocalTime open_time;
    LocalTime close_time;
    String image;
    Integer status;
}
