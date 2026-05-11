package com.example.DoAnCDIO3.mapper;

import com.example.DoAnCDIO3.dto.request.BookingCreateRequest;
import com.example.DoAnCDIO3.dto.response.BookingResponse;
import com.example.DoAnCDIO3.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    // =======================================================
    // 1. TỪ ENTITY -> RESPONSE (Trả về cho Frontend)
    // =======================================================
    // MapStruct sẽ chui vào object 'user_id' để lấy biến 'full_name' đè vào 'customer_name'
    @Mapping(source = "user_id.full_name", target = "customer_name")
    // MapStruct sẽ chui vào object 'field_id' để lấy biến 'name' đè vào 'field_name'
    @Mapping(source = "field_id.name", target = "field_name")
    BookingResponse toBookingResponse(Booking booking);

    // =======================================================
    // 2. TỪ REQUEST -> ENTITY (Dùng cho CreateBooking)
    // =======================================================
    @Mapping(target = "booking_id", ignore = true)     // Tự động tăng nên bỏ qua
    @Mapping(target = "booking_code", ignore = true)   // Sẽ random tạo ở Service
    @Mapping(target = "user_id", ignore = true)        // Phải query DB rồi set bằng tay
    @Mapping(target = "field_id", ignore = true)       // Request là Integer, Entity là Object nên bỏ qua
    @Mapping(target = "total_amount", ignore = true)   // Sẽ tính toán ở Service
    @Mapping(target = "status", ignore = true)         // Mặc định là 0 (Chờ duyệt)
    @Mapping(target = "created_at", ignore = true)
    @Mapping(target = "updated_at", ignore = true)
    Booking toBooking(BookingCreateRequest request);

    // =======================================================
    // 3. TỪ REQUEST -> CẬP NHẬT ENTITY CÓ SẴN (Dùng cho UpdateBooking)
    // =======================================================
    @Mapping(target = "booking_id", ignore = true)
    @Mapping(target = "booking_code", ignore = true)
    @Mapping(target = "user_id", ignore = true)
    @Mapping(target = "field_id", ignore = true)
    @Mapping(target = "total_amount", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "created_at", ignore = true)
    @Mapping(target = "updated_at", ignore = true)
    void updateBookingFromRequest(BookingCreateRequest request, @MappingTarget Booking booking);
}
