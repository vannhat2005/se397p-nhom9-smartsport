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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class FieldPriceServiceImpl implements FieldPriceService {
    FieldPriceRepository fieldPriceRepository;
    FieldRepository fieldRepository;
    FieldPriceMapper fieldPriceMapper;


    @Override
    public FieldPriceResponse createFieldPrice(FieldPriceRequest request) {
        // 1. Validate thời gian
        if (!request.getEnd_time().isAfter(request.getStart_time())) {
            throw new AppException(ErrorCode.PRICE_TIME_INVALID);
        }

        // 2. Tìm sân bóng
        Field field = fieldRepository.findById(request.getField_id())
                .orElseThrow(() -> new AppException(ErrorCode.FIELD_NOT_FOUND));

        // 3. Dùng Mapper chuyển từ Request sang Entity chỉ trong 1 nốt nhạc
        FieldPrice fieldPrice = fieldPriceMapper.toFieldPrice(request);
        fieldPrice.setField_id(field); // Set thủ công thuộc tính Field vì Mapper đang ignore

        // 4. Lưu vào Database
        FieldPrice savedFieldPrice = fieldPriceRepository.save(fieldPrice);

        // 5. Dùng Mapper trả về Response
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
        Page<FieldPrice> fieldPricePage = fieldPriceRepository.findByField_id_Id(fieldId, pageRequest);

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
}
