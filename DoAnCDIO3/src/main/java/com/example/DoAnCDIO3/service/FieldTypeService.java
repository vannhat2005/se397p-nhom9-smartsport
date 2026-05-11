package com.example.DoAnCDIO3.service;

import com.example.DoAnCDIO3.dto.PageResponse;
import com.example.DoAnCDIO3.dto.request.FieldTypeRequest;
import com.example.DoAnCDIO3.dto.response.FieldTypeResponse;

import java.util.List;

public interface FieldTypeService {
    // 1. Tạo mới loại sân
    FieldTypeResponse createFieldType(FieldTypeRequest request);

    // 2. Lấy tất cả loại sân (dùng cho dropdown chọn loại sân)
    PageResponse<FieldTypeResponse> getAllFieldTypes(int page, int size);
    // 3. Lấy chi tiết loại sân theo ID
    FieldTypeResponse getFieldTypeById(Integer id);

    // 4. Cập nhật loại sân
    FieldTypeResponse updateFieldType(Integer id, FieldTypeRequest request);

    // 5. Xóa loại sân (Xóa mềm)
    void deleteFieldType(Integer id);
}
