package com.ms.user_service.services;

import com.ms.user_service.dtos.LoginRequestDto;
import com.ms.user_service.dtos.SignupRequestDto;
import com.ms.user_service.dtos.UserDto;
import com.ms.user_service.entities.UserEntity;
import com.ms.user_service.exceptions.DataNotFoundException;
import com.ms.user_service.exceptions.DuplicateDataException;
import com.ms.user_service.mappers.UserMapper;
import com.ms.user_service.repositories.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserDto signup(SignupRequestDto body, HttpServletResponse response) {
        UserEntity userEntity = userRepository.findByEmail(body.getEmail()).orElse(null);
        if (userEntity != null) {
            throw new DuplicateDataException("Email already exist");
        }
        UserEntity newUser = UserMapper.toEntity(body);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser);
        String token = jwtService.generateToken(newUser.getId().toString(), null);
        response.setHeader("x-access-token",token);
        return UserMapper.toDto(newUser);
    }


    public UserDto login(LoginRequestDto body, HttpServletResponse response) {
        UserEntity userEntity = userRepository.findByEmail(body.getEmail())
                .orElseThrow(()->new DataNotFoundException("Email not found"));

        String token = jwtService.generateToken(userEntity.getId().toString(), null);
        response.setHeader("x-access-token",token);
        return UserMapper.toDto(userEntity);
    }

}
