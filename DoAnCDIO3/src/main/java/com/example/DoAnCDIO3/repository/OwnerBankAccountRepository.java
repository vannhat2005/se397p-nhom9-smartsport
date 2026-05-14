package com.example.DoAnCDIO3.repository;

import com.example.DoAnCDIO3.entity.OwnerBankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerBankAccountRepository extends JpaRepository<OwnerBankAccount,Integer> {
    // Tìm 1 tài khoản duy nhất của chủ sân
    @Query("SELECT o FROM OwnerBankAccount o WHERE o.user_id.id = :ownerId")
    Optional<OwnerBankAccount> findByUser_Id(Integer ownerId);

    // Kiểm tra xem chủ sân đã có tài khoản hay chưa (Dùng cho hàm Create).
    @Query("SELECT COUNT(o) > 0 FROM OwnerBankAccount o WHERE o.user_id.id = :ownerId")
    boolean existsByUser_Id(Integer ownerId);
}
