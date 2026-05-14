package com.example.DoAnCDIO3.controller;

import com.example.DoAnCDIO3.dto.ApiResponse;
import com.example.DoAnCDIO3.dto.PageResponse;
import com.example.DoAnCDIO3.dto.request.BookingCreateRequest;
import com.example.DoAnCDIO3.dto.response.BookingResponse;
import com.example.DoAnCDIO3.service.BookingService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingController {
    BookingService bookingService;

    // ===================================================================
    // API DÀNH CHO KHÁCH HÀNG (CUSTOMER)
    // ===================================================================

    /**
     * 1. Khách hàng tạo đơn đặt sân mới
     */
    @PostMapping
    public ApiResponse<BookingResponse> createBooking(
            @RequestHeader("X-User-Id") Integer customerId, // Lấy từ người đang đăng nhập
            @RequestBody @Valid BookingCreateRequest request
    ) {
        return ApiResponse.<BookingResponse>builder()
                .message("Đặt sân thành công. Vui lòng chờ chủ sân xác nhận!")
                .data(bookingService.createBooking(customerId, request))
                .build();
    }

    /**
     * 2. Khách hàng xem lịch sử các sân mình đã đặt
     */
    @GetMapping("/customer")
    public ApiResponse<PageResponse<BookingResponse>> getBookingsByCustomer(
            @RequestHeader("X-User-Id") Integer customerId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.<PageResponse<BookingResponse>>builder()
                .message("Lấy lịch sử đặt sân thành công.")
                .data(bookingService.getBookingsByCustomer(customerId, page, size))
                .build();
    }


    // ===================================================================
    // API DÀNH CHO CHỦ SÂN (OWNER)
    // ===================================================================

    /**
     * 3. Chủ sân xem danh sách khách hàng đặt sân của mình
     */
    @GetMapping("/owner")
    public ApiResponse<PageResponse<BookingResponse>> getBookingsByOwner(
            @RequestHeader("X-User-Id") Integer ownerId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.<PageResponse<BookingResponse>>builder()
                .message("Lấy danh sách đơn đặt sân thành công.")
                .data(bookingService.getBookingsByOwner(ownerId, page, size))
                .build();
    }

    /**
     * 4. Chủ sân Duyệt hoặc Từ chối đơn đặt sân
     * @param status Trạng thái xử lý (Ví dụ: 1 = Duyệt, 2 = Từ chối)
     */
    @PutMapping("/{bookingId}/process")
    public ApiResponse<BookingResponse> processBooking(
            @PathVariable Integer bookingId,
            @RequestHeader("X-User-Id") Integer ownerId,
            @RequestParam Integer status
    ) {
        // Tùy chỉnh câu thông báo trả về Frontend cho mượt
        String message = (status == 1) ? "Đã duyệt đơn đặt sân thành công." : "Đã từ chối đơn đặt sân.";

        return ApiResponse.<BookingResponse>builder()
                .message(message)
                .data(bookingService.processBooking(bookingId, ownerId, status))
                .build();
    }
}
