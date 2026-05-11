package com.example.DoAnCDIO3.mapper;

import com.example.DoAnCDIO3.dto.request.FieldCreateRequest;
import com.example.DoAnCDIO3.dto.response.FieldResponse;
import com.example.DoAnCDIO3.entity.Field;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface FieldMapper {
//    // Khi map qua Field, bỏ qua trường ID (tránh lỗi đè ID)
//    @Mapping(target = "id", ignore = true)
//    Field toField(FieldCreateRequest request);
//
//    // CHỈ ĐỊNH RÕ NGUỒN LẤY DỮ LIỆU CHO 2 TRƯỜNG NÀY
//    @Mapping(source = "user_id.full_name", target = "owner_name")
//    @Mapping(source = "field_type_id.name", target = "field_type_name")
//    FieldResponse toFieldResponse(Field field);
//
//    @Mapping(target = "id", ignore = true)
//    void updateFieldFromRequest(FieldCreateRequest request, @MappingTarget Field field);

    // 1. Map lúc TẠO MỚI (Bỏ qua id, user_id và field_type_id)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user_id", ignore = true)
    @Mapping(target = "field_type_id", ignore = true) // <-- Dòng fix lỗi ở đây
    Field toField(FieldCreateRequest request);

    // 2. Map lúc TRẢ VỀ (Chỉ định rõ nguồn lấy tên chủ sân và tên loại sân)
    @Mapping(source = "user_id.full_name", target = "owner_name")
    @Mapping(source = "field_type_id.name", target = "field_type_name")
    FieldResponse toFieldResponse(Field field);

    // 3. Map lúc CẬP NHẬT (Bỏ qua id, user_id và field_type_id)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user_id", ignore = true)
    @Mapping(target = "field_type_id", ignore = true) // <-- Dòng fix lỗi ở đây
    void updateFieldFromRequest(FieldCreateRequest request, @MappingTarget Field field);
}
