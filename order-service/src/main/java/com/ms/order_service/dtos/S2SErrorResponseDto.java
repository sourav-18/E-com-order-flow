package com.ms.order_service.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class S2SErrorResponseDto {
    private  Boolean success;
    private  Integer status;
    private  String message;
    private  LocalDateTime timestamp=LocalDateTime.now();

    public S2SErrorResponseDto() {
    }

    public S2SErrorResponseDto(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
