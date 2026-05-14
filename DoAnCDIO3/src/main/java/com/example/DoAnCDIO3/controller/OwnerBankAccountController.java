package com.example.DoAnCDIO3.controller;

import com.example.DoAnCDIO3.dto.ApiResponse;
import com.example.DoAnCDIO3.dto.request.OwnerBankAccountRequest;
import com.example.DoAnCDIO3.dto.response.OwnerBankAccountResponse;
import com.example.DoAnCDIO3.service.OwnerBankAccountService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owner-bank-account")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OwnerBankAccountController {
    OwnerBankAccountService bankAccountService;

    /**
     * 1. Thêm tài khoản ngân hàng mới
     */
    @PostMapping
    public ApiResponse<OwnerBankAccountResponse> createBankAccount(
            @RequestBody @Valid OwnerBankAccountRequest request
    ) {
        return ApiResponse.<OwnerBankAccountResponse>builder()
                .message("Thêm tài khoản ngân hàng thành công.")
                .data(bankAccountService.createBankAccount(request))
                .build();
    }

    /**
     * 2. Xem tài khoản ngân hàng của chủ sân đang đăng nhập
     */
    @GetMapping("/my-account")
    public ApiResponse<OwnerBankAccountResponse> getBankAccountByOwner(
            @RequestHeader("X-User-Id") Integer ownerId
    ) {
        return ApiResponse.<OwnerBankAccountResponse>builder()
                .message("Lấy thông tin tài khoản ngân hàng thành công.")
                .data(bankAccountService.getBankAccountByOwner(ownerId))
                .build();
    }

    /**
     * 3. Cập nhật tài khoản ngân hàng (Chỉ chủ tài khoản mới được sửa)
     */
    @PutMapping("/{id}")
    public ApiResponse<OwnerBankAccountResponse> updateBankAccount(
            @PathVariable Integer id,
            @RequestBody @Valid OwnerBankAccountRequest request,
            @RequestHeader("X-User-Id") Integer ownerId
    ) {
        return ApiResponse.<OwnerBankAccountResponse>builder()
                .message("Cập nhật tài khoản ngân hàng thành công.")
                .data(bankAccountService.updateBankAccount(id, request, ownerId))
                .build();
    }
}
