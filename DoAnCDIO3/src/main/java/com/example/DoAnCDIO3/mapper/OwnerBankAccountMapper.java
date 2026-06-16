package com.example.DoAnCDIO3.mapper;

import com.example.DoAnCDIO3.dto.request.OwnerBankAccountRequest;
import com.example.DoAnCDIO3.dto.response.OwnerBankAccountResponse;
import com.example.DoAnCDIO3.entity.OwnerBankAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OwnerBankAccountMapper {
    // 1. Entity -> Response
    @Mapping(source = "user_id.id", target = "user_id")
    @Mapping(source = "user_id.full_name", target = "owner_name") // Giả định bảng User có biến full_name
    OwnerBankAccountResponse toResponse(OwnerBankAccount bankAccount);

    // 2. Request -> Entity (Create)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user_id", ignore = true) // Sẽ query DB và set thủ công
    OwnerBankAccount toEntity(OwnerBankAccountRequest request);

    // 3. Request -> Entity (Update)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user_id", ignore = true)
    void updateEntityFromRequest(OwnerBankAccountRequest request, @MappingTarget OwnerBankAccount bankAccount);
}
