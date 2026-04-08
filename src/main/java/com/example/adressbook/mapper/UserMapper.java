package com.example.adressbook.mapper;

import com.example.adressbook.entity.User;
import com.example.adressbook.dto.request.UserCreateRequest;
import com.example.adressbook.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", ignore = true)
    User toEntity(UserCreateRequest dto);
    UserResponse toResponse(User user);
}
