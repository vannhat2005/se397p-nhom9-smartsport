package com.example.DoAnCDIO3.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL) // Ẩn field 'data' nếu nó bị null (như lúc báo lỗi)
public class ApiResponse<T> {
    @Builder.Default
    LocalDateTime timestamp = LocalDateTime.now();
    private int code = 200;
    private String message = "Thành công";
    private T data;
}
