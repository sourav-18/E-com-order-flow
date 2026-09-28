package com.ms.user_service.mappers;

import com.ms.user_service.dtos.SignupRequestDto;
import com.ms.user_service.dtos.UserDto;
import com.ms.user_service.entities.UserEntity;

public class UserMapper {
    public static UserEntity toEntity(SignupRequestDto dto){
        return UserEntity.builder().name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

    public static UserDto toDto(UserEntity entity){
        return UserDto.builder().id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .build();
    }

}
