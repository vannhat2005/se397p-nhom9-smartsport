package com.example.DoAnCDIO3.service.ServiceImpl;

import com.example.DoAnCDIO3.dto.PageResponse;
import com.example.DoAnCDIO3.dto.request.FieldTypeRequest;
import com.example.DoAnCDIO3.dto.response.FieldTypeResponse;
import com.example.DoAnCDIO3.entity.FieldType;
import com.example.DoAnCDIO3.enums.FieldTypeStatusEnum;
import com.example.DoAnCDIO3.exception.AppException;
import com.example.DoAnCDIO3.exception.ErrorCode;
import com.example.DoAnCDIO3.mapper.FieldTypeMapper;
import com.example.DoAnCDIO3.repository.FieldTypeRepository;
import com.example.DoAnCDIO3.service.FieldTypeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class FieldTypeServiceImpl implements FieldTypeService {
    FieldTypeRepository fieldTypeRepository;
    FieldTypeMapper fieldTypeMapper;

    @Override
    public FieldTypeResponse createFieldType(FieldTypeRequest request) {
        // Kiểm tra xem tên loại sân đã tồn tại chưa
        if (fieldTypeRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.FIELD_TYPE_EXISTED);
        }

        // Map từ Request sang Entity
        FieldType fieldType = fieldTypeMapper.toFieldType(request);

        fieldType.setStatus(FieldTypeStatusEnum.ACTIVE.getValue());

        // Lưu vào DB
        FieldType savedFieldType = fieldTypeRepository.save(fieldType);

        return fieldTypeMapper.toFieldTypeResponse(savedFieldType);
    }

    @Override
    public PageResponse<FieldTypeResponse> getAllFieldTypes(int page, int size) {
        // 1. Tạo đối tượng Pageable (Spring Boot đếm trang từ 0 nên ta trừ 1)
        Pageable pageable = PageRequest.of(page - 1, size);

        // 2. Lấy dữ liệu phân trang từ Database
        Page<FieldType> pageData = fieldTypeRepository.findAll(pageable);

        // 3. Chuyển đổi từ Entity sang DTO
        List<FieldTypeResponse> fieldTypeList = pageData.getContent().stream()
                .map(fieldTypeMapper::toFieldTypeResponse)
                .toList();

        // 4. Đóng gói vào PageResponse và trả về
        return PageResponse.<FieldTypeResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(fieldTypeList)
                .build();
    }


    @Override
    public FieldTypeResponse getFieldTypeById(Integer id) {
        return null;
    }

    @Override
    public FieldTypeResponse updateFieldType(Integer id, FieldTypeRequest request) {
        FieldType fieldType = fieldTypeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FIELD_TYPE_NOT_FOUND));

        // Kiểm tra nếu tên thay đổi thì tên mới có bị trùng với loại sân khác không
        if (fieldTypeRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.FIELD_TYPE_EXISTED);
        }
        fieldType.setStatus(FieldTypeStatusEnum.INACTIVE.getValue());
        // MapStruct cập nhật dữ liệu từ request đắp vào entity hiện tại
        fieldTypeMapper.updateFieldTypeFromRequest(request, fieldType);

        // Lưu cập nhật vào DB
        FieldType updatedFieldType = fieldTypeRepository.save(fieldType);

        return fieldTypeMapper.toFieldTypeResponse(updatedFieldType);
    }

    @Override
    public void deleteFieldType(Integer id) {
        FieldType fieldType = fieldTypeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FIELD_TYPE_NOT_FOUND));

        // Xóa mềm: Chuyển status về 0 (Ngừng hoạt động)
        fieldType.setStatus(FieldTypeStatusEnum.INACTIVE.getValue());
        fieldTypeRepository.save(fieldType);
    }
}
