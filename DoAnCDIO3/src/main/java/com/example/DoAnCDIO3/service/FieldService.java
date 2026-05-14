package com.example.DoAnCDIO3.service;

import com.example.DoAnCDIO3.dto.PageResponse;
import com.example.DoAnCDIO3.dto.request.FieldCreateRequest;
import com.example.DoAnCDIO3.dto.response.FieldResponse;

public interface FieldService {
    // Tạo sân mới (Cần truyền ID của chủ sân đang đăng nhập)
    FieldResponse createField(Integer ownerId, FieldCreateRequest request);

    // 1. Dành cho Khách hàng: Lấy danh sách sân đã duyệt (ACTIVE)
    PageResponse<FieldResponse> getAllActiveFields(int page, int size);

    // 2. Dành cho Admin: Lấy danh sách sân đang chờ duyệt (PENDING)
    PageResponse<FieldResponse> getAllPendingFields(int page, int size);

    // 3. Dành cho Admin: Duyệt (ACTIVE) hoặc Từ chối (REJECTED/INACTIVE) sân bóng
    FieldResponse approveOrRejectField(Integer id, boolean isApproved);

    // Xem chi tiết một sân theo ID
    FieldResponse getFieldById(Integer id);

    // Cập nhật thông tin sân
    FieldResponse updateField(Integer id, Integer ownerId, FieldCreateRequest request);

    // Xóa sân (Xóa mềm)
    void deleteField(Integer id);
}
