package com.ms.inventory_service.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderDto {
    private Long id;
    private Long productId;
    private Integer quantity;
    private Integer price;
    private Integer finalTotalPrice;
}
