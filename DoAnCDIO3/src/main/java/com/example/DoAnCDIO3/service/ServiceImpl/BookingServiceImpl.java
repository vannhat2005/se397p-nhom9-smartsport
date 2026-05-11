package com.example.DoAnCDIO3.service.ServiceImpl;


import com.example.DoAnCDIO3.dto.PageResponse;
import com.example.DoAnCDIO3.dto.request.BookingCreateRequest;
import com.example.DoAnCDIO3.dto.response.BookingResponse;
import com.example.DoAnCDIO3.entity.Booking;
import com.example.DoAnCDIO3.entity.Field;
import com.example.DoAnCDIO3.entity.FieldPrice;
import com.example.DoAnCDIO3.entity.User;
import com.example.DoAnCDIO3.exception.AppException;
import com.example.DoAnCDIO3.exception.ErrorCode;
import com.example.DoAnCDIO3.mapper.BookingMapper;
import com.example.DoAnCDIO3.repository.BookingRepository;
import com.example.DoAnCDIO3.repository.FieldPriceRepository;
import com.example.DoAnCDIO3.repository.FieldRepository;
import com.example.DoAnCDIO3.repository.UserRepository;
import com.example.DoAnCDIO3.service.BookingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingServiceImpl implements BookingService {

    BookingRepository bookingRepository;
    UserRepository userRepository;
    FieldRepository fieldRepository;
    FieldPriceRepository fieldPriceRepository; 
    BookingMapper bookingMapper;


    @Override
    public BookingResponse createBooking(Integer customerId, BookingCreateRequest request) {
        // 1. Validate giờ giấc
        if (!request.getEnd_time().isAfter(request.getStart_time())) {
            throw new AppException(ErrorCode.BOOKING_TIME_INVALID);
        }

        // 2. Tìm User và Field
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Field field = fieldRepository.findById(request.getField_id())
                .orElseThrow(() -> new AppException(ErrorCode.FIELD_NOT_FOUND));

        // 3. Kiểm tra trùng lịch
        boolean isOverlapping = bookingRepository.existsOverlappingBooking(
                field.getId(),
                request.getBooking_date(),
                request.getStart_time(),
                request.getEnd_time()
        );
        if (isOverlapping) {
            throw new AppException(ErrorCode.BOOKING_OVERLAPPED);
        }

        // ==========================================
        // 4. LẤY GIÁ CHUẨN TỪ DATABASE DỰA VÀO NGÀY VÀ GIỜ
        // ==========================================
        // Xác định là ngày thường hay cuối tuần
        DayOfWeek dayOfWeek = request.getBooking_date().getDayOfWeek();
        int currentDayType = (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) ? 2 : 1;

        // Truy vấn Database để lấy mức giá
        FieldPrice fieldPrice = fieldPriceRepository.findPriceForBooking(
                field.getId(),
                currentDayType,
                request.getStart_time()
        ).orElseThrow(() -> new AppException(ErrorCode.PRICE_NOT_FOUND)); // Bắn lỗi nếu chủ sân chưa cài đặt giá cho giờ này

        // Tính toán tổng tiền
        long minutes = Duration.between(request.getStart_time(), request.getEnd_time()).toMinutes();
        BigDecimal totalAmount = fieldPrice.getPrice().multiply(BigDecimal.valueOf((double) minutes / 60));
        // ==========================================

        // 5. Tạo mã Booking ngẫu nhiên
        String dateStr = request.getBooking_date().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String randomStr = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        String bookingCode = "BK-" + dateStr + "-" + randomStr;

        // 6. Dùng MapStruct tạo Entity
        Booking booking = bookingMapper.toBooking(request);
        booking.setBooking_code(bookingCode);
        booking.setUser_id(customer);
        booking.setField_id(field);
        booking.setTotal_amount(totalAmount); // Gắn giá trị thực tế vào đây
        booking.setStatus(0);
        booking.setCreated_at(LocalDateTime.now());

        // 7. Lưu và trả về
        Booking savedBooking = bookingRepository.save(booking);
        return bookingMapper.toBookingResponse(savedBooking);
    }

    @Override
    public PageResponse<BookingResponse> getAllBookings(int page, int size) {
        return null;
    }
}
