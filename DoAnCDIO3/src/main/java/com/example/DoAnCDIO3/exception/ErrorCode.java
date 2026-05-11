package com.example.DoAnCDIO3.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // ==========================================
    // 0. MÃ CHUNG & HỆ THỐNG
    // ==========================================
    SUCCESS(200, "Thành công", HttpStatus.OK),
    UNCATEGORIZED_EXCEPTION(9999, "Lỗi hệ thống không xác định", HttpStatus.INTERNAL_SERVER_ERROR),
    VALIDATION_ERROR(4000, "Dữ liệu đầu vào không hợp lệ", HttpStatus.BAD_REQUEST),

    // ==========================================
    // 1. MODULE USER (1000 - 1099)
    // ==========================================
    ROLE_NOT_NULL(1001, "Vui lòng chọn vai trò (Role).", HttpStatus.BAD_REQUEST),
    FULLNAME_NOT_BLANK(1002, "Họ tên không được để trống.", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_BLANK(1003, "Email không được để trống.", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1004, "Định dạng email không hợp lệ.", HttpStatus.BAD_REQUEST),
    PHONE_INVALID(1005, "Số điện thoại phải gồm 10 chữ số.", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_BLANK(1006, "Mật khẩu không được để trống.", HttpStatus.BAD_REQUEST),
    PASSWORD_LENGTH_INVALID(1007, "Mật khẩu phải có ít nhất 6 ký tự.", HttpStatus.BAD_REQUEST),

    // ==========================================
    // 2. MODULE FIELD & FIELD TYPE (1100 - 1199)
    // ==========================================
    FIELD_TYPE_NOT_NULL(1101, "Vui lòng chọn loại sân.", HttpStatus.BAD_REQUEST),
    FIELD_NAME_NOT_BLANK(1102, "Tên sân không được để trống.", HttpStatus.BAD_REQUEST),
    FIELD_ADDRESS_NOT_BLANK(1103, "Địa chỉ sân không được để trống.", HttpStatus.BAD_REQUEST),
    FIELD_TIME_NOT_NULL(1104, "Thời gian mở/đóng cửa không được để trống.", HttpStatus.BAD_REQUEST),
    FIELD_TYPE_NAME_NOT_BLANK(1105, "Tên loại sân không được để trống.", HttpStatus.BAD_REQUEST),

    // ==========================================
    // 3. MODULE FIELD PRICE (1200 - 1299)
    // ==========================================
    PRICE_FIELD_NOT_NULL(1201, "Mã sân không được để trống.", HttpStatus.BAD_REQUEST),
    PRICE_DAY_TYPE_NOT_NULL(1202, "Loại ngày (thường/cuối tuần) không được để trống.", HttpStatus.BAD_REQUEST),
    PRICE_NOT_NULL(1203, "Giá tiền không được để trống.", HttpStatus.BAD_REQUEST),
    PRICE_MIN_INVALID(1204, "Giá tiền phải lớn hơn 0.", HttpStatus.BAD_REQUEST),

    // ==========================================
    // 4. MODULE BOOKING (1300 - 1399)
    // ==========================================
    BOOKING_FIELD_NOT_NULL(1301, "Mã sân không được để trống.", HttpStatus.BAD_REQUEST),
    BOOKING_DATE_NOT_NULL(1302, "Ngày đặt sân không được để trống.", HttpStatus.BAD_REQUEST),
    BOOKING_DATE_PAST(1303, "Ngày đặt sân phải từ hôm nay trở đi.", HttpStatus.BAD_REQUEST),
    BOOKING_TIME_NOT_NULL(1304, "Giờ bắt đầu và kết thúc không được để trống.", HttpStatus.BAD_REQUEST),

    // ==========================================
    // 5. MODULE PAYMENT & BANK ACCOUNT (1400 - 1499)
    // ==========================================
    PAYMENT_BOOKING_NOT_NULL(1401, "Mã đặt sân không được để trống.", HttpStatus.BAD_REQUEST),
    PAYMENT_BANK_NOT_BLANK(1402, "Tên ngân hàng không được để trống.", HttpStatus.BAD_REQUEST),
    PAYMENT_ACCOUNT_NOT_BLANK(1403, "Tên chủ tài khoản không được để trống.", HttpStatus.BAD_REQUEST),

    BANK_OWNER_NOT_NULL(1404, "Mã chủ sân không được để trống.", HttpStatus.BAD_REQUEST),
    BANK_NAME_NOT_BLANK(1405, "Tên ngân hàng không được để trống.", HttpStatus.BAD_REQUEST),
    BANK_NUMBER_NOT_BLANK(1406, "Số tài khoản không được để trống.", HttpStatus.BAD_REQUEST),
    BANK_HOLDER_NOT_BLANK(1407, "Tên người thụ hưởng không được để trống.", HttpStatus.BAD_REQUEST),

    // ==========================================
    // MODULE USER
    USER_EXISTED(1008, "Email này đã được đăng ký trong hệ thống.", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1009, "Không tìm thấy thông tin người dùng.", HttpStatus.NOT_FOUND),
    ROLE_NOT_FOUND(1010, "Không tìm thấy vai trò (Role) này trong hệ thống.", HttpStatus.NOT_FOUND),

    // ==========================================
    // MODULE PHÂN QUYỀN & BẢO MẬT (4000 - 4099)
    UNAUTHORIZED_ACTION(4030, "Bạn không có quyền thực hiện thao tác này.", HttpStatus.FORBIDDEN),

    // ==========================================
    // MODULE FIELD TYPE - LOẠI SÂN (1100 - 1199)
    FIELD_TYPE_EXISTED(1106, "Tên loại sân này đã tồn tại.", HttpStatus.BAD_REQUEST),
    FIELD_TYPE_NOT_FOUND(1107, "Không tìm thấy thông tin loại sân.", HttpStatus.NOT_FOUND),

    // ==========================================
    // MODULE FIELD - SÂN BÓNG (1200 - 1299)
    FIELD_NOT_FOUND(1201, "Không tìm thấy thông tin sân bóng.", HttpStatus.NOT_FOUND),

    // ==========================================
    // Module Booking (1300-1399)
    BOOKING_TIME_INVALID(1305, "Giờ kết thúc phải sau giờ bắt đầu.", HttpStatus.BAD_REQUEST),
    BOOKING_OVERLAPPED(1306, "Rất tiếc, sân bóng vào khung giờ này đã có người đặt.", HttpStatus.CONFLICT),

    PRICE_NOT_FOUND(1206, "Không tìm thấy bảng giá thiết lập cho khung giờ này.", HttpStatus.NOT_FOUND),
    PRICE_TIME_INVALID(1205, "Giờ kết thúc phải sau giờ bắt đầu.", HttpStatus.BAD_REQUEST);


    private final int code;
    private final String message;
    private final HttpStatus statusCode;

    ErrorCode(int code, String message, HttpStatus statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
