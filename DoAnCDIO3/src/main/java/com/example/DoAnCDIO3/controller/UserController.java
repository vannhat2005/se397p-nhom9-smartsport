package com.example.DoAnCDIO3.controller;

import com.example.DoAnCDIO3.dto.ApiResponse;
import com.example.DoAnCDIO3.dto.PageResponse;
import com.example.DoAnCDIO3.dto.request.UserCreateByAdminRequest;
import com.example.DoAnCDIO3.dto.request.UserCreateRequest;
import com.example.DoAnCDIO3.dto.response.UserResponse;
import com.example.DoAnCDIO3.dto.update.UserUpdateRequest;
import com.example.DoAnCDIO3.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    // 1. TẠO MỚI USER
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Trả về HTTP Status 201
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .code(201)
                .message("Tạo người dùng thành công")
                .data(userService.createUser(request))
                .build();
    }

    // 2. LẤY DANH SÁCH USER (CÓ PHÂN TRANG)
    @GetMapping
    public ApiResponse<PageResponse<UserResponse>> getAllUsers(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        // Không truyền code và message, nó sẽ tự lấy mặc định là 200 và "Thành công"
        return ApiResponse.<PageResponse<UserResponse>>builder()
                .data(userService.getAllUsers(page, size))
                .build();
    }

    // 3. LẤY THÔNG TIN USER THEO ID
    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable Integer id) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.getUserById(id))
                .build();
    }

    // 4. CẬP NHẬT THÔNG TIN USER
    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(
            @PathVariable Integer id,
            @RequestBody @Valid UserUpdateRequest request
    ) {
        return ApiResponse.<UserResponse>builder()
                .message("Cập nhật thông tin thành công")
                .data(userService.updateUser(id, request))
                .build();
    }

    // 5. XÓA USER (Xóa mềm)
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ApiResponse.<Void>builder()
                .message("Đã xóa người dùng thành công")
                // Không truyền data thì JsonInclude.NON_NULL sẽ tự động ẩn field data đi
                .build();
    }

    /**
     * 6. Admin tạo tài khoản mới và cấp quyền chỉ định (Ví dụ: OWNER, ADMIN)
     */
    @PostMapping("/admin/create")
    // @PreAuthorize("hasAuthority('ADMIN')") // Mở khóa dòng này khi cài Spring Security
    public ApiResponse<UserResponse> createUserByAdmin(
            @RequestBody @Valid UserCreateByAdminRequest request
    ) {
        return ApiResponse.<UserResponse>builder()
                .message("Admin đã tạo tài khoản thành công.")
                .data(userService.createUserByAdmin(request))
                .build();
    }

    /**
     * 7. Admin cập nhật lại quyền (Role) cho một User đang có trong hệ thống
     */
    @PutMapping("/admin/{userId}/role")
    // @PreAuthorize("hasAuthority('ADMIN')") // Mở khóa dòng này khi cài Spring Security
    public ApiResponse<UserResponse> updateRole(
            @PathVariable Integer userId,
            @RequestParam String roleName
    ) {
        return ApiResponse.<UserResponse>builder()
                .message("Cập nhật quyền (Role) cho người dùng thành công.")
                .data(userService.updateRole(userId, roleName))
                .build();
    }
}
