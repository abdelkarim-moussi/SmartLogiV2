package com.app.api.mapper;

import com.app.api.dto.user.CreateUserRequest;
import com.app.api.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(CreateUserRequest request);
}