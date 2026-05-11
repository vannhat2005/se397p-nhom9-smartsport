package com.example.DoAnCDIO3.service.ServiceImpl;

import com.example.DoAnCDIO3.dto.request.OwnerBankAccountRequest;
import com.example.DoAnCDIO3.dto.response.OwnerBankAccountResponse;
import com.example.DoAnCDIO3.entity.OwnerBankAccount;
import com.example.DoAnCDIO3.exception.AppException;
import com.example.DoAnCDIO3.exception.ErrorCode;
import com.example.DoAnCDIO3.mapper.OwnerBankAccountMapper;
import com.example.DoAnCDIO3.repository.OwnerBankAccountRepository;
import com.example.DoAnCDIO3.repository.UserRepository;
import com.example.DoAnCDIO3.service.OwnerBankAccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class OwnerBankAccountServiceImpl implements OwnerBankAccountService {
    OwnerBankAccountRepository bankAccountRepository;
    UserRepository userRepository;
    OwnerBankAccountMapper bankAccountMapper;
    @Override
    public OwnerBankAccountResponse createBankAccount(OwnerBankAccountRequest request) {
        return null;
    }

    @Override
    public OwnerBankAccountResponse getBankAccountByOwner(Integer ownerId) {
        // Lấy đúng 1 tài khoản của chủ sân, nếu không có thì báo lỗi NOT_FOUND
        OwnerBankAccount account = bankAccountRepository.findByUser_Id(ownerId)
                .orElseThrow(() -> new AppException(ErrorCode.BANK_ACCOUNT_NOT_FOUND));

        return bankAccountMapper.toResponse(account);
    }

    @Override
    public OwnerBankAccountResponse updateBankAccount(Integer id, OwnerBankAccountRequest request, Integer ownerId) {
        // 1. Tìm tài khoản
        OwnerBankAccount existingAccount = bankAccountRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BANK_ACCOUNT_NOT_FOUND));

        // 2. Bảo mật (IDOR): Check xem người sửa có đúng là chủ sở hữu không
        if (!existingAccount.getUser_id().getId().equals(ownerId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED_ACTION);
        }

        // 3. Cập nhật data
        bankAccountMapper.updateEntityFromRequest(request, existingAccount);

        // 4. Lưu lại
        OwnerBankAccount updatedAccount = bankAccountRepository.save(existingAccount);
        return bankAccountMapper.toResponse(updatedAccount);
    }
}
