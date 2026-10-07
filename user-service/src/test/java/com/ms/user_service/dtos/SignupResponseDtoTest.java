package com.ms.user_service.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SignupResponseDtoTest {
    private Long id;
    private String name;
    private String token;
}
