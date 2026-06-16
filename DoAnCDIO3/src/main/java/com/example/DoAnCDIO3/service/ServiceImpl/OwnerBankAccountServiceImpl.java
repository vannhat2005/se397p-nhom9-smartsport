package com.example.DoAnCDIO3.service.ServiceImpl;

import com.example.DoAnCDIO3.dto.request.OwnerBankAccountRequest;
import com.example.DoAnCDIO3.dto.response.OwnerBankAccountResponse;
import com.example.DoAnCDIO3.entity.OwnerBankAccount;
import com.example.DoAnCDIO3.entity.User;
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
        // Nếu bạn đặt là userId thì dùng getUserId()
        Integer ownerId = request.getUser_id();

        // 2. Tìm xem User (chủ sân) này có tồn tại trong bảng users không
        User user = userRepository.findById(ownerId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // 3. Kiểm tra xem chủ sân này đã khai báo tài khoản ngân hàng chưa (Mỗi người 1 tài khoản)
        // Lưu ý: Hàm existsByUser_Id này chúng ta đã viết bằng @Query ở bước trước
        if (bankAccountRepository.existsByUser_Id(ownerId)) {
            // Nếu bạn chưa có mã lỗi này trong ErrorCode, hãy vào đó thêm vào nhé!
            // Ví dụ: BANK_ACCOUNT_EXISTED(400, "Chủ sân này đã có tài khoản ngân hàng!")
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        // 4. Map từ DTO sang Entity
        OwnerBankAccount bankAccount = bankAccountMapper.toEntity(request);

        // 5. Gắn đối tượng User vào tài khoản ngân hàng
        bankAccount.setUser_id(user);

        // 6. Lưu vào cơ sở dữ liệu
        OwnerBankAccount savedAccount = bankAccountRepository.save(bankAccount);

        // 7. Trả về kết quả
        return bankAccountMapper.toResponse(savedAccount);
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
