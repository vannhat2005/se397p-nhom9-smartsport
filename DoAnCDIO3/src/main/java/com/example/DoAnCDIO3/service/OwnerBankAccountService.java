package com.example.DoAnCDIO3.service;

import com.example.DoAnCDIO3.dto.request.OwnerBankAccountRequest;
import com.example.DoAnCDIO3.dto.response.OwnerBankAccountResponse;

public interface OwnerBankAccountService {
    OwnerBankAccountResponse createBankAccount(OwnerBankAccountRequest request);

    OwnerBankAccountResponse getBankAccountByOwner(Integer ownerId);

    OwnerBankAccountResponse updateBankAccount(Integer id, OwnerBankAccountRequest request, Integer ownerId);

}
