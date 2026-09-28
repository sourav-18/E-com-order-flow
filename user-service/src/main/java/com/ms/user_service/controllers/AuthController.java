package com.ms.user_service.controllers;

import com.ms.user_service.dtos.ApiResponseDto;
import com.ms.user_service.dtos.LoginRequestDto;
import com.ms.user_service.dtos.SignupRequestDto;
import com.ms.user_service.dtos.UserDto;
import com.ms.user_service.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public Object login(@Valid @RequestBody LoginRequestDto body, HttpServletResponse response){
        UserDto dto = userService.login(body,response);
        ApiResponseDto<UserDto>apiResponse=new ApiResponseDto<>(200,"User login successfully",dto);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto<UserDto>> signup(@Valid @RequestBody SignupRequestDto body, HttpServletResponse response){
        UserDto dto = userService.signup(body,response);
        ApiResponseDto<UserDto>apiResponse=new ApiResponseDto<>(201,"User signup successfully",dto);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
}
