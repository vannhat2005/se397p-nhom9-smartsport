package com.example.DoAnCDIO3.repository;

import com.example.DoAnCDIO3.entity.OwnerBankAccount;
import com.example.DoAnCDIO3.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerBankAccountRepository extends JpaRepository<OwnerBankAccount,Integer> {
}
