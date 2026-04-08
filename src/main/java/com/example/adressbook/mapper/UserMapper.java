package com.example.adressbook.mapper;

import com.example.adressbook.entity.User;
import com.example.adressbook.dto.request.UserCreateRequest;
import com.example.adressbook.dto.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserCreateRequest dto);
    UserResponse toResponse(User user);
}
