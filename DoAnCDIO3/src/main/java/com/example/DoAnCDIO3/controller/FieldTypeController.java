package com.example.DoAnCDIO3.controller;

import com.example.DoAnCDIO3.dto.ApiResponse;
import com.example.DoAnCDIO3.dto.PageResponse;
import com.example.DoAnCDIO3.dto.request.FieldTypeRequest;
import com.example.DoAnCDIO3.dto.response.FieldTypeResponse;
import com.example.DoAnCDIO3.service.FieldTypeService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/field-types")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FieldTypeController {
    FieldTypeService fieldTypeService;

    // 1. TẠO MỚI LOẠI SÂN
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Trả về HTTP Status 201
    public ApiResponse<FieldTypeResponse> createFieldType(@RequestBody @Valid FieldTypeRequest request) {
        return ApiResponse.<FieldTypeResponse>builder()
                .code(201)
                .message("Tạo loại sân thành công")
                .data(fieldTypeService.createFieldType(request))
                .build();
    }

    // 2. LẤY DANH SÁCH LOẠI SÂN (CÓ PHÂN TRANG)
    @GetMapping
    public ApiResponse<PageResponse<FieldTypeResponse>> getAllFieldTypes(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ApiResponse.<PageResponse<FieldTypeResponse>>builder()
                .data(fieldTypeService.getAllFieldTypes(page, size))
                .build();
    }

    // 3. LẤY CHI TIẾT 1 LOẠI SÂN THEO ID
    @GetMapping("/{id}")
    public ApiResponse<FieldTypeResponse> getFieldTypeById(@PathVariable Integer id) {
        return ApiResponse.<FieldTypeResponse>builder()
                .data(fieldTypeService.getFieldTypeById(id))
                .build();
    }

    // 4. CẬP NHẬT LOẠI SÂN
    @PutMapping("/{id}")
    public ApiResponse<FieldTypeResponse> updateFieldType(
            @PathVariable Integer id,
            @RequestBody @Valid FieldTypeRequest request
    ) {
        return ApiResponse.<FieldTypeResponse>builder()
                .message("Cập nhật loại sân thành công")
                .data(fieldTypeService.updateFieldType(id, request))
                .build();
    }

    // 5. XÓA LOẠI SÂN (XÓA MỀM)
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteFieldType(@PathVariable Integer id) {
        fieldTypeService.deleteFieldType(id);
        return ApiResponse.<Void>builder()
                .message("Đã xóa loại sân thành công")
                .build();
    }
}
