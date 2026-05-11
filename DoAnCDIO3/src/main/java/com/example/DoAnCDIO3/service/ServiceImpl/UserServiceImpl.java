package com.example.DoAnCDIO3.service.ServiceImpl;

import com.example.DoAnCDIO3.dto.PageResponse;
import com.example.DoAnCDIO3.dto.request.UserCreateRequest;
import com.example.DoAnCDIO3.dto.response.UserResponse;
import com.example.DoAnCDIO3.dto.update.UserUpdateRequest;
import com.example.DoAnCDIO3.entity.Role;
import com.example.DoAnCDIO3.entity.User;
import com.example.DoAnCDIO3.enums.RoleEnum;
import com.example.DoAnCDIO3.enums.UserStatusEnum;
import com.example.DoAnCDIO3.exception.AppException;
import com.example.DoAnCDIO3.exception.ErrorCode;
import com.example.DoAnCDIO3.mapper.UserMapper;
import com.example.DoAnCDIO3.repository.RoleRepository;
import com.example.DoAnCDIO3.repository.UserRepository;
import com.example.DoAnCDIO3.service.UserService;
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
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
//    PasswordEncoder passwordEncoder; // Đã cấu hình ở ApplicationConfig
    UserMapper userMapper;

    // 1. TẠO MỚI (CREATE)
    public UserResponse createUser(UserCreateRequest request) {
        // Kiểm tra email trùng
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        // Tạo Entity mới
        User user = userMapper.toUser(request);

        Role role = roleRepository.findByName(RoleEnum.USER.toString()).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        user.setRole_id(role);
        user.setStatus(UserStatusEnum.ACTIVE.getValue());
        user.setCreated_at(LocalDateTime.now());

        // chưa cấu hình security nên chưa làm password
        user.setPassword("");
        // Lưu vào DB
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    // 2. LẤY TẤT CẢ (GET ALL)
    public PageResponse<UserResponse> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<User> pageData = userRepository.findAll(pageable);

        List<UserResponse> userList = pageData.getContent().stream()
                .map(userMapper::toUserResponse) // Sử dụng Mapper của bạn
                .toList();

        return PageResponse.<UserResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(userList)
                .build();
    }

    // 3. LẤY THEO ID (GET BY ID)
    public UserResponse getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponse(user);
    }

    // 4. CẬP NHẬT (UPDATE)
    public UserResponse updateUser(Integer id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Cập nhật các trường cho phép
        userMapper.updateUserFromRequest(request,user);

        if(request.getStatus() != null) {
            user.setStatus(request.getStatus());
        }

        user.setUpdated_at(LocalDateTime.now());

        return userMapper.toUserResponse(user);
    }

    // 5. XÓA (DELETE)
    public void deleteUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        user.setStatus(UserStatusEnum.INACTIVE.getValue());
        user.setUpdated_at(LocalDateTime.now());
        userRepository.save(user);
    }


}
