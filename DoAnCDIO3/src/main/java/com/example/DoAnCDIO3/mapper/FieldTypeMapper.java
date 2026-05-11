package com.example.DoAnCDIO3.mapper;

import com.example.DoAnCDIO3.dto.request.FieldTypeRequest;
import com.example.DoAnCDIO3.dto.response.FieldTypeResponse;
import com.example.DoAnCDIO3.entity.FieldType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FieldTypeMapper {
    FieldType toFieldType(FieldTypeRequest request);
    FieldTypeResponse toFieldTypeResponse(FieldType fieldType);
    void updateFieldTypeFromRequest(FieldTypeRequest request, @MappingTarget FieldType fieldType);
}
