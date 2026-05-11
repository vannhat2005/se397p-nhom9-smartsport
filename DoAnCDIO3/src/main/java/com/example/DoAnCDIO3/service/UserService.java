package com.example.DoAnCDIO3.service;

import com.example.DoAnCDIO3.dto.PageResponse;
import com.example.DoAnCDIO3.dto.request.UserCreateRequest;
import com.example.DoAnCDIO3.dto.response.UserResponse;
import com.example.DoAnCDIO3.dto.update.UserUpdateRequest;

public interface UserService {
    // 1. Tạo mới User
    UserResponse createUser(UserCreateRequest request);

    // 2. Lấy danh sách User (có phân trang)
    PageResponse<UserResponse> getAllUsers(int page, int size);

    // 3. Lấy User theo ID
    UserResponse getUserById(Integer id);

    // 4. Cập nhật User
    UserResponse updateUser(Integer id, UserUpdateRequest request);

    // 5. Xóa User (Xóa mềm)
    void deleteUser(Integer id);
}
