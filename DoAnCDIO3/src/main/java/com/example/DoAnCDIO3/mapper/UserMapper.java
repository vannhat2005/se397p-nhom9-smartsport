package com.example.DoAnCDIO3.mapper;

import com.example.DoAnCDIO3.dto.request.UserCreateRequest;
import com.example.DoAnCDIO3.dto.response.UserResponse;
import com.example.DoAnCDIO3.dto.update.UserUpdateRequest;
import com.example.DoAnCDIO3.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface  UserMapper {
    User toUser(UserCreateRequest request);
    UserResponse toUserResponse(User user);
    void updateUserFromRequest(UserUpdateRequest request, @MappingTarget User user);
}
