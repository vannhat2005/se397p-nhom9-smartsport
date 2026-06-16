package com.example.DoAnCDIO3.controller;

import com.example.DoAnCDIO3.dto.ApiResponse;
import com.example.DoAnCDIO3.dto.PageResponse;
import com.example.DoAnCDIO3.dto.request.FieldCreateRequest;
import com.example.DoAnCDIO3.dto.response.FieldAndPriceResponse;
import com.example.DoAnCDIO3.dto.response.FieldResponse;
import com.example.DoAnCDIO3.service.FieldService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fields")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FieldController {

    FieldService fieldService;

    // ===================================================================
    // 1. API DÀNH CHO CHỦ SÂN (OWNER)
    // ===================================================================

    /**
     * Chủ sân tạo sân bóng mới (Cần chờ Admin duyệt)
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<FieldResponse> createField(
            @RequestHeader("X-User-Id") Integer ownerId,
            @RequestBody @Valid FieldCreateRequest request
    ) {
        return ApiResponse.<FieldResponse>builder()
                .code(201)
                .message("Gửi yêu cầu tạo sân thành công, vui lòng chờ Admin duyệt.")
                .data(fieldService.createField(ownerId, request))
                .build();
    }

    /**
     * Chủ sân cập nhật thông tin sân bóng
     */
    @PutMapping("/{id}")
    public ApiResponse<FieldResponse> updateField(
            @PathVariable Integer id,
            @RequestHeader("X-User-Id") Integer ownerId,
            @RequestBody @Valid FieldCreateRequest request
    ) {
        return ApiResponse.<FieldResponse>builder()
                .message("Cập nhật thông tin sân bóng thành công.")
                .data(fieldService.updateField(id, ownerId, request))
                .build();
    }

    /**
     * Xóa sân bóng (Xóa mềm)
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteField(@PathVariable Integer id) {
        fieldService.deleteField(id);
        return ApiResponse.<Void>builder()
                .message("Đã xóa sân bóng thành công.")
                .build();
    }

    // ===================================================================
    // 2. API DÀNH CHO KHÁCH HÀNG / PUBLIC (CUSTOMER)
    // ===================================================================

    /**
     * Lấy danh sách sân đã duyệt (ACTIVE) để hiển thị cho khách hàng đặt sân
     */
    @GetMapping("/active")
    public ApiResponse<PageResponse<FieldResponse>> getAllActiveFields(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.<PageResponse<FieldResponse>>builder()
                .message("Lấy danh sách sân bóng thành công.")
                .data(fieldService.getAllActiveFields(page, size))
                .build();
    }

    /**
     * Xem chi tiết 1 sân bóng (Bao gồm thông tin sân + toàn bộ bảng giá)
     */
    @GetMapping("/{id}/details")
    public ApiResponse<FieldAndPriceResponse> getFieldDetailWithPrices(@PathVariable Integer id) {
        return ApiResponse.<FieldAndPriceResponse>builder()
                .message("Lấy thông tin chi tiết sân thành công.")
                .data(fieldService.getFieldDetailWithPrices(id))
                .build();
    }

    // ===================================================================
    // 3. API DÀNH CHO QUẢN TRỊ VIÊN (ADMIN)
    // ===================================================================

    /**
     * Admin lấy danh sách các sân đang chờ duyệt (PENDING)
     */
    @GetMapping("/admin/pending")
    public ApiResponse<PageResponse<FieldResponse>> getAllPendingFields(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.<PageResponse<FieldResponse>>builder()
                .message("Lấy danh sách sân chờ phê duyệt thành công.")
                .data(fieldService.getAllPendingFields(page, size))
                .build();
    }

    /**
     * Admin Duyệt (ACTIVE) hoặc Từ chối (REJECTED/INACTIVE) sân bóng
     */
    @PutMapping("/admin/{id}/approve")
    public ApiResponse<FieldResponse> approveOrRejectField(
            @PathVariable Integer id,
            @RequestParam boolean isApproved
    ) {
        String message = isApproved ? "Đã duyệt sân thành công." : "Đã từ chối sân bóng.";
        return ApiResponse.<FieldResponse>builder()
                .message(message)
                .data(fieldService.approveOrRejectField(id, isApproved))
                .build();
    }
}