package com.ms.user_service.controllers;

import com.ms.user_service.dtos.ApiResponseDto;
import com.ms.user_service.dtos.UserDto;
import com.ms.user_service.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<ApiResponseDto<UserDto>> profile(HttpServletRequest request) {
        Long userId = Long.valueOf(request.getHeader("user-id"));
        UserDto profile = userService.profile(userId);
        ApiResponseDto<UserDto> apiResponse = new ApiResponseDto<>
                (200, "profile details successfully", profile);
        return ResponseEntity.status(200).body(apiResponse);
    }
}
