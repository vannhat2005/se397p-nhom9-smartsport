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
public class FieldTypeRequest {
    @NotBlank(message = "FIELD_TYPE_NAME_NOT_BLANK")
    String name;

    String description;

}
