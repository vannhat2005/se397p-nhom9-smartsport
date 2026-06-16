package com.example.DoAnCDIO3.controller;

import com.example.DoAnCDIO3.dto.ApiResponse;
import com.example.DoAnCDIO3.dto.PageResponse;
import com.example.DoAnCDIO3.dto.request.FieldPriceRequest;
import com.example.DoAnCDIO3.dto.response.FieldPriceResponse;
import com.example.DoAnCDIO3.service.FieldPriceService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/field-prices")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FieldPriceController {
    FieldPriceService fieldPriceService;

    /**
     * 1. Chủ sân tạo mới một mức giá cho sân
     */
    @PostMapping
    public ApiResponse<FieldPriceResponse> createFieldPrice(
            @RequestBody @Valid FieldPriceRequest request
    ) {
        return ApiResponse.<FieldPriceResponse>builder()
                .message("Tạo bảng giá cho sân thành công.")
                .data(fieldPriceService.createFieldPrice(request))
                .build();
    }

    /**
     * 2. Lấy danh sách giá của một sân cụ thể (Có phân trang)
     */
    @GetMapping("/field/{fieldId}")
    public ApiResponse<PageResponse<FieldPriceResponse>> getFieldPricesByFieldId(
            @PathVariable Integer fieldId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.<PageResponse<FieldPriceResponse>>builder()
                .message("Lấy danh sách bảng giá thành công.")
                .data(fieldPriceService.getFieldPricesByFieldId(fieldId, page, size))
                .build();
    }

    /**
     * 3. Chủ sân cập nhật thông tin mức giá
     */
    @PutMapping("/{id}")
    public ApiResponse<FieldPriceResponse> updateFieldPrice(
            @PathVariable Integer id,
            @RequestBody @Valid FieldPriceRequest request
    ) {
        return ApiResponse.<FieldPriceResponse>builder()
                .message("Cập nhật bảng giá thành công.")
                .data(fieldPriceService.updateFieldPrice(id, request))
                .build();
    }

    /**
     * 4. Chủ sân xóa một mức giá
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteFieldPrice(
            @PathVariable Integer id
    ) {
        fieldPriceService.deleteFieldPrice(id);
        return ApiResponse.<Void>builder()
                .message("Xóa bảng giá thành công.")
                .build();
    }

    @GetMapping("/field/{fieldId}/daily-prices")
    public ApiResponse<List<FieldPriceResponse>> getPricesByDate(
            @PathVariable Integer fieldId
    ) {
        return ApiResponse.<List<FieldPriceResponse>>builder()
                .message("Lấy danh sách giá theo ngày thành công.")
                .data(fieldPriceService.getPricesByDate(fieldId))
                .build();
    }
}
