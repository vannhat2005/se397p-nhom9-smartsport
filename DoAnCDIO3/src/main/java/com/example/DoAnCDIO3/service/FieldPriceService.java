package com.example.DoAnCDIO3.service;


import com.example.DoAnCDIO3.dto.PageResponse;
import com.example.DoAnCDIO3.dto.request.FieldPriceRequest;
import com.example.DoAnCDIO3.dto.response.FieldPriceResponse;

import java.time.LocalDate;
import java.util.List;

public interface FieldPriceService {
    // 1. Tạo mới một mức giá cho sân
    FieldPriceResponse createFieldPrice(FieldPriceRequest request);

    // 2. THAY ĐỔI Ở ĐÂY: Lấy giá có phân trang
    PageResponse<FieldPriceResponse> getFieldPricesByFieldId(Integer fieldId, int page, int size);

    // 3. Cập nhật thông tin giá
    FieldPriceResponse updateFieldPrice(Integer id, FieldPriceRequest request);

    // 4. Xóa một mức giá
    void deleteFieldPrice(Integer id);

    List<FieldPriceResponse> getPricesByDate(Integer fieldId);
}
