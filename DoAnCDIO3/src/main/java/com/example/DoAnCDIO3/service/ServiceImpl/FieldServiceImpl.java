package com.example.DoAnCDIO3.service.ServiceImpl;

import com.example.DoAnCDIO3.dto.PageResponse;
import com.example.DoAnCDIO3.dto.request.FieldCreateRequest;
import com.example.DoAnCDIO3.dto.response.FieldAndPriceResponse;
import com.example.DoAnCDIO3.dto.response.FieldPriceResponse;
import com.example.DoAnCDIO3.dto.response.FieldResponse;
import com.example.DoAnCDIO3.entity.Field;
import com.example.DoAnCDIO3.entity.FieldPrice;
import com.example.DoAnCDIO3.entity.FieldType;
import com.example.DoAnCDIO3.entity.User;
import com.example.DoAnCDIO3.enums.FieldEnum;
import com.example.DoAnCDIO3.exception.AppException;
import com.example.DoAnCDIO3.exception.ErrorCode;
import com.example.DoAnCDIO3.mapper.FieldMapper;
import com.example.DoAnCDIO3.mapper.FieldPriceMapper;
import com.example.DoAnCDIO3.repository.FieldPriceRepository;
import com.example.DoAnCDIO3.repository.FieldRepository;
import com.example.DoAnCDIO3.repository.FieldTypeRepository;
import com.example.DoAnCDIO3.repository.UserRepository;
import com.example.DoAnCDIO3.service.FieldService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {
    FieldRepository fieldRepository;
    UserRepository userRepository;
    FieldTypeRepository fieldTypeRepository;
    FieldMapper fieldMapper;
    FieldPriceRepository fieldPriceRepository;
    FieldPriceMapper fieldPriceMapper;

    @Override
    public FieldResponse createField(Integer ownerId, FieldCreateRequest request) {
        // 1. Tìm thông tin Chủ sân (User)
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // 2. Tìm thông tin Loại sân (FieldType)
        FieldType fieldType = fieldTypeRepository.findById(request.getField_type_id())
                .orElseThrow(() -> new AppException(ErrorCode.FIELD_TYPE_NOT_FOUND));

        // 3. Map DTO sang Entity
        Field field = fieldMapper.toField(request);

        // 4. Set các giá trị quan hệ và mặc định
        field.setUser_id(owner);
        field.setField_type_id(fieldType);
        field.setStatus(FieldEnum.PENDING.getValue()); // 2: Đang chờ duyệt
        field.setCreated_at(LocalDateTime.now());

        // 5. Lưu và trả về DTO
        Field savedField = fieldRepository.save(field);
        return fieldMapper.toFieldResponse(savedField);
    }

    @Override
    public PageResponse<FieldResponse> getAllActiveFields(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        // Truyền giá trị 1 (ACTIVE) vào hàm tìm kiếm
        Page<Field> pageData = fieldRepository.findByStatus(FieldEnum.ACTIVE.getValue(), pageable);

        return buildPageResponse(pageData, page);
    }

    @Override
    public PageResponse<FieldResponse> getAllPendingFields(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        // Truyền giá trị 2 (PENDING) vào hàm tìm kiếm
        Page<Field> pageData = fieldRepository.findByStatus(FieldEnum.PENDING.getValue(), pageable);

        return buildPageResponse(pageData, page);
    }

    @Override
    public FieldResponse approveOrRejectField(Integer id, boolean isApproved) {
        // 1. Tìm sân trong Database
        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FIELD_NOT_FOUND));

        // 2. Cập nhật trạng thái dựa trên quyết định của Admin
        if (isApproved) {
            field.setStatus(FieldEnum.ACTIVE.getValue()); // Giả sử 1 là ACTIVE (Đã duyệt)
            // TODO: (Tùy chọn) Gửi email hoặc thông báo cho chủ sân là sân đã được duyệt
        } else {
            field.setStatus(FieldEnum.INACTIVE.getValue()); // Giả sử 2 là REJECTED (Từ chối duyệt)
            // TODO: (Tùy chọn) Gửi thông báo lý do từ chối
        }

        // 3. Lưu lại và trả về kết quả
        Field savedField = fieldRepository.save(field);
        return fieldMapper.toFieldResponse(savedField);
    }

    @Override
    public FieldAndPriceResponse getFieldDetailWithPrices(Integer id) {
        // 1. Tìm thông tin sân
        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FIELD_NOT_FOUND));
        FieldResponse fieldResponse = fieldMapper.toFieldResponse(field);

        // 2. Tìm danh sách giá của sân đó
        List<FieldPrice> prices = fieldPriceRepository.findAllPricesByFieldId(id);

        // 3. Map từ List Entity sang List DTO
        List<FieldPriceResponse> priceResponses = prices.stream()
                .map(fieldPriceMapper::toFieldPriceResponse)
                .toList();

        // 4. Gói tất cả vào FieldDetailResponse và trả về
        return FieldAndPriceResponse.builder()
                .field_info(fieldResponse)
                .prices(priceResponses)
                .build();
    }


    @Override
    public FieldResponse updateField(Integer id, Integer ownerId, FieldCreateRequest request) {
        // 1. Tìm sân theo ID
        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FIELD_NOT_FOUND));

        // 2. KIỂM TRA QUYỀN SỞ HỮU (Security Check)
        // So sánh ID của chủ sân lưu trong DB với ID của người đang call API
        if (!field.getUser_id().getId().equals(ownerId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED_ACTION);
        }

        // 3. Nếu đổi loại sân, kiểm tra loại sân mới có tồn tại không
        FieldType fieldType = fieldTypeRepository.findById(request.getField_type_id())
                .orElseThrow(() -> new AppException(ErrorCode.FIELD_TYPE_NOT_FOUND));

        // 4. Map dữ liệu update
        fieldMapper.updateFieldFromRequest(request, field);

        // Cập nhật lại fieldType và thời gian
        field.setField_type_id(fieldType);
        field.setUpdated_at(LocalDateTime.now());

        // 5. Lưu vào DB
        Field updatedField = fieldRepository.save(field);
        return fieldMapper.toFieldResponse(updatedField);
    }

    @Override
    public void deleteField(Integer id) {
        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FIELD_NOT_FOUND));

        // Xóa mềm: Chuyển status = 0 (Ngừng hoạt động / Tạm đóng cửa)
        field.setStatus(FieldEnum.INACTIVE.getValue());
        field.setUpdated_at(LocalDateTime.now());
        fieldRepository.save(field);
    }


    private PageResponse<FieldResponse> buildPageResponse(Page<Field> pageData, int page) {
        List<FieldResponse> fieldList = pageData.getContent().stream()
                .map(fieldMapper::toFieldResponse)
                .toList();

        return PageResponse.<FieldResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(fieldList)
                .build();
    }
}
