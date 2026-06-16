package com.example.DoAnCDIO3.mapper;

import com.example.DoAnCDIO3.dto.request.UserCreateByAdminRequest;
import com.example.DoAnCDIO3.dto.request.UserCreateRequest;
import com.example.DoAnCDIO3.dto.response.UserResponse;
import com.example.DoAnCDIO3.dto.update.UserUpdateRequest;
import com.example.DoAnCDIO3.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface  UserMapper {
    User toUser(UserCreateRequest request);
    @Mapping(source = "role_id.id", target = "role_id")
    @Mapping(source = "role_id.name", target = "role_name")
    UserResponse toUserResponse(User user);
    void updateUserFromRequest(UserUpdateRequest request, @MappingTarget User user);
    User toUserFromAdminRequest(UserCreateByAdminRequest request);
}
