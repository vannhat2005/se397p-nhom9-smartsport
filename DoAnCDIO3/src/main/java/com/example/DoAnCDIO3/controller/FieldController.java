package com.example.DoAnCDIO3.controller;

import com.example.DoAnCDIO3.dto.ApiResponse;
import com.example.DoAnCDIO3.dto.PageResponse;
import com.example.DoAnCDIO3.dto.request.FieldCreateRequest;
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

    // 1. TẠO SÂN MỚI
    // Tạm thời truyền ownerId qua Header để test, sau này dùng JWT sẽ lấy tự động
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<FieldResponse> createField(
            @RequestHeader("X-Owner-Id") Integer ownerId,
            @RequestBody @Valid FieldCreateRequest request
    ) {
        return ApiResponse.<FieldResponse>builder()
                .code(201)
                .message("Yêu cầu tạo sân đã được gửi, vui lòng chờ Admin duyệt.")
                .data(fieldService.createField(ownerId, request))
                .build();
    }

    // 2. DÀNH CHO KHÁCH HÀNG: LẤY DANH SÁCH SÂN ĐÃ DUYỆT (ACTIVE)
    @GetMapping("/active")
    public ApiResponse<PageResponse<FieldResponse>> getAllActiveFields(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ApiResponse.<PageResponse<FieldResponse>>builder()
                .data(fieldService.getAllActiveFields(page, size))
                .build();
    }

    // 3. DÀNH CHO ADMIN: LẤY DANH SÁCH SÂN CHỜ DUYỆT (PENDING)
    @GetMapping("/pending")
    public ApiResponse<PageResponse<FieldResponse>> getAllPendingFields(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ApiResponse.<PageResponse<FieldResponse>>builder()
                .message("Danh sách sân đang chờ phê duyệt")
                .data(fieldService.getAllPendingFields(page, size))
                .build();
    }

    // 4. XEM CHI TIẾT SÂN
    @GetMapping("/{id}")
    public ApiResponse<FieldResponse> getFieldById(@PathVariable Integer id) {
        return ApiResponse.<FieldResponse>builder()
                .data(fieldService.getFieldById(id))
                .build();
    }

    // 5. CẬP NHẬT THÔNG TIN SÂN
    @PutMapping("/{id}")
    public ApiResponse<FieldResponse> updateField(
            @PathVariable Integer id,
            @RequestHeader("X-Owner-Id") Integer ownerId,
            @RequestBody @Valid FieldCreateRequest request
    ) {
        return ApiResponse.<FieldResponse>builder()
                .message("Cập nhật thông tin sân thành công")
                .data(fieldService.updateField(id, ownerId, request))
                .build();
    }

    // 6. XÓA SÂN (XÓA MỀM)
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteField(@PathVariable Integer id) {
        fieldService.deleteField(id);
        return ApiResponse.<Void>builder()
                .message("Đã xóa sân bóng thành công")
                .build();
    }

    // API dành cho Admin duyệt/từ chối sân
    @PutMapping("/admin/{id}/approve")
    public ApiResponse<FieldResponse> approveOrRejectField(
            @PathVariable Integer id,
            @RequestParam boolean isApproved
    ) {
        String message = isApproved ? "Đã duyệt sân thành công." : "Đã từ chối sân.";
        return ApiResponse.<FieldResponse>builder()
                .message(message)
                .data(fieldService.approveOrRejectField(id, isApproved))
                .build();
    }
}
