package com.example.DoAnCDIO3.dto.response;

import com.example.DoAnCDIO3.entity.FieldPrice;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FieldAndPriceResponse {
    FieldResponse field_info;
    List<FieldPriceResponse> prices;
}
