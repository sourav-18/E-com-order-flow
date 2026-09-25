package com.ms.inventory_service.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductReserveCreateResponseDto {
    private Long orderId;
    private Integer totalAmount;
    private String nextApiUrl;
}
