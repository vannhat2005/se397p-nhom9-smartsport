package com.example.DoAnCDIO3.service.ServiceImpl;

import com.example.DoAnCDIO3.dto.PageResponse;
import com.example.DoAnCDIO3.dto.request.FieldPriceRequest;
import com.example.DoAnCDIO3.dto.response.FieldPriceResponse;
import com.example.DoAnCDIO3.entity.Field;
import com.example.DoAnCDIO3.entity.FieldPrice;
import com.example.DoAnCDIO3.exception.AppException;
import com.example.DoAnCDIO3.exception.ErrorCode;
import com.example.DoAnCDIO3.mapper.FieldPriceMapper;
import com.example.DoAnCDIO3.repository.FieldPriceRepository;
import com.example.DoAnCDIO3.repository.FieldRepository;
import com.example.DoAnCDIO3.service.FieldPriceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class FieldPriceServiceImpl implements FieldPriceService {
    FieldPriceRepository fieldPriceRepository;
    FieldRepository fieldRepository;
    FieldPriceMapper fieldPriceMapper;


    @Override
    public FieldPriceResponse createFieldPrice(FieldPriceRequest request) {
        // 1. Validate thời gian cơ bản (Giờ kết thúc phải sau giờ bắt đầu)
        if (!request.getEnd_time().isAfter(request.getStart_time())) {
            throw new AppException(ErrorCode.PRICE_TIME_INVALID);
        }

        // 2. Tìm sân bóng (Tìm trước để lấy được giờ hoạt động của sân)
        Field field = fieldRepository.findById(request.getField_id())
                .orElseThrow(() -> new AppException(ErrorCode.FIELD_NOT_FOUND));

        // 3. LOGIC MỚI: Kiểm tra thời gian setup giá phải nằm trong giờ mở/đóng cửa của sân
        // Giả sử bảng Field của sếp có 2 biến là open_time và close_time
        if (request.getStart_time().isBefore(field.getOpen_time()) ||
                request.getEnd_time().isAfter(field.getClose_time())) {

            // Sếp nhớ thêm mã lỗi này vào enum ErrorCode nhé (VD: "Thời gian giá tiền vượt quá giờ hoạt động của sân!")
            throw new AppException(ErrorCode.PRICE_TIME_OUT_OF_BOUNDS);
        }

        // 4. Dùng Mapper chuyển từ Request sang Entity
        FieldPrice fieldPrice = fieldPriceMapper.toFieldPrice(request);

        // Lưu ý: Hôm trước chúng ta đã thống nhất đổi tên biến trong Entity thành 'field' rồi nhé
        fieldPrice.setField_id(field);

        // 5. Lưu vào Database
        FieldPrice savedFieldPrice = fieldPriceRepository.save(fieldPrice);

        // 6. Dùng Mapper trả về Response
        return fieldPriceMapper.toFieldPriceResponse(savedFieldPrice);
    }

    @Override
    public PageResponse<FieldPriceResponse> getFieldPricesByFieldId(Integer fieldId, int page, int size) {
        // 1. Kiểm tra sân có tồn tại không
        Field field = fieldRepository.findById(fieldId)
                .orElseThrow(() -> new AppException(ErrorCode.FIELD_NOT_FOUND));

        // 2. Tạo đối tượng phân trang (page - 1 vì Spring Boot đếm từ 0)
        PageRequest pageRequest = PageRequest.of(page - 1, size);

        // 3. Query Database
        Page<FieldPrice> fieldPricePage = fieldPriceRepository.getPricesByFieldId(fieldId, pageRequest);

        // 4. Map List Entity sang List Response DTO bằng Stream API và Mapper
        List<FieldPriceResponse> responseList = fieldPricePage.getContent().stream()
                .map(fieldPriceMapper::toFieldPriceResponse) // Gọi Mapper ở đây
                .toList();

        // 5. Đóng gói vào PageResponse
        return PageResponse.<FieldPriceResponse>builder()
                .currentPage(page)
                .totalPages(fieldPricePage.getTotalPages())
                .pageSize(fieldPricePage.getSize())
                .totalElements(fieldPricePage.getTotalElements())
                .data(responseList)
                .build();
    }

    @Override
    public FieldPriceResponse updateFieldPrice(Integer id, FieldPriceRequest request) {
        // 1. Validate thời gian
        if (!request.getEnd_time().isAfter(request.getStart_time())) {
            throw new AppException(ErrorCode.PRICE_TIME_INVALID);
        }

        // 2. Tìm giá cũ trong DB
        FieldPrice existingPrice = fieldPriceRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)); // TODO: Có thể đổi thành FIELD_PRICE_NOT_FOUND

        // 3. Tìm sân bóng
        Field field = fieldRepository.findById(request.getField_id())
                .orElseThrow(() -> new AppException(ErrorCode.FIELD_NOT_FOUND));

        // 4. Cập nhật dữ liệu
        fieldPriceMapper.updateFieldPriceFromRequest(request, existingPrice);
        existingPrice.setField_id(field);

        // 5. Lưu lại
        FieldPrice updatedPrice = fieldPriceRepository.save(existingPrice);

        return fieldPriceMapper.toFieldPriceResponse(updatedPrice);
    }

    @Override
    public void deleteFieldPrice(Integer id) {

    }

    @Override
    public List<FieldPriceResponse> getPricesByDate(Integer fieldId) {
        // [NÂNG CẤP 1]: Kiểm tra sân tồn tại
        if (!fieldRepository.existsById(fieldId)) {
            throw new AppException(ErrorCode.FIELD_NOT_FOUND);
        }

        // [NÂNG CẤP 2]: Tự động lấy ngày hôm nay theo chuẩn múi giờ Việt Nam (Tránh lỗi lệch giờ khi deploy server)
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh"));

        // 1. Phân tích xem HÔM NAY là thứ mấy
        DayOfWeek dayOfWeek = today.getDayOfWeek();

        // 2. Phân loại: T7, CN thì dayType = 2 (Cuối tuần). Các ngày còn lại (T2->T6) dayType = 1 (Ngày thường)
        int currentDayType = (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) ? 2 : 1;

        // 3. Gọi Repository lấy giá
        List<FieldPrice> prices = fieldPriceRepository.findActivePricesByDayType(fieldId, currentDayType);

        // 4. Map sang DTO và trả về
        return prices.stream()
                .map(fieldPriceMapper::toFieldPriceResponse)
                .toList();
    }
}
