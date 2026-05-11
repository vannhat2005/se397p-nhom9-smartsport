package com.example.DoAnCDIO3.mapper;

import com.example.DoAnCDIO3.dto.request.FieldPriceRequest;
import com.example.DoAnCDIO3.dto.response.FieldPriceResponse;
import com.example.DoAnCDIO3.entity.FieldPrice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FieldPriceMapper {
    // Map từ Entity sang Response
    @Mapping(source = "field_id.id", target = "field_id") // Lấy id của sân
    @Mapping(source = "field_id.name", target = "field_name") // Lấy tên của sân
    FieldPriceResponse toFieldPriceResponse(FieldPrice fieldPrice);

    // Map từ Request sang Entity
    // (Bỏ qua map trường field_id vì trong Service chúng ta phải tìm đối tượng Field từ DB rồi mới set vào)
    @Mapping(target = "field_id", ignore = true)
    FieldPrice toFieldPrice(FieldPriceRequest request);

    @Mapping(target = "id", ignore = true) // Không bao giờ update khóa chính
    @Mapping(target = "field_id", ignore = true) // Bỏ qua field_id vì ta sẽ set thủ công bằng Object
    void updateFieldPriceFromRequest(FieldPriceRequest request, @MappingTarget FieldPrice fieldPrice);
}
