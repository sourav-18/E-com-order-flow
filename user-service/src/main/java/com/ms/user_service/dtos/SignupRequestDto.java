package com.ms.user_service.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    @NotBlank
    @Size(min = 2,max = 200)
    private String name;

    @NotBlank
    @Size(min = 7,max = 200)
    private String email;

    @NotBlank
    @Size(min = 4,max = 200)
    private String password;
}
