package com.example.DoAnCDIO3.controller;

import com.example.DoAnCDIO3.dto.ApiResponse;
import com.example.DoAnCDIO3.dto.response.FieldScheduleResponse;
import com.example.DoAnCDIO3.service.FieldScheduleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/field-schedules")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FieldScheduleController {
    FieldScheduleService fieldScheduleService;

    /**
     * API Lấy danh sách các khung giờ ĐÃ BỊ ĐẶT của 1 sân trong 1 ngày cụ thể
     * Dùng cho Frontend vô hiệu hóa (disable) hoặc tô màu các khung giờ không còn trống.
     */
    @GetMapping("/field/{fieldId}/booked-slots")
    public ApiResponse<List<FieldScheduleResponse>> getBookedSchedules(
            @PathVariable Integer fieldId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return ApiResponse.<List<FieldScheduleResponse>>builder()
                .message("Lấy danh sách giờ đã đặt thành công.")
                .data(fieldScheduleService.getBookedSchedules(fieldId, date))
                .build();
    }
}
