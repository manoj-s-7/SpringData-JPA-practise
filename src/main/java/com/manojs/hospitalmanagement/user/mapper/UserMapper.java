package com.manojs.hospitalmanagement.user.mapper;

import com.manojs.hospitalmanagement.security.dto.SignUpRequestDto;
import com.manojs.hospitalmanagement.security.dto.SignUpResponseDto;
import com.manojs.hospitalmanagement.user.entity.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(SignUpRequestDto dto);
    SignUpResponseDto toDto(User user);
}
