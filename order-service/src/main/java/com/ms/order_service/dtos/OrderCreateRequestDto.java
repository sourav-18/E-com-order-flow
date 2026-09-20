package com.ms.order_service.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCreateRequestDto {
    @NotNull
    @Min(1)
    private Long productId;

    @NotNull
    @Min(1)
    private Integer quantity;
}
