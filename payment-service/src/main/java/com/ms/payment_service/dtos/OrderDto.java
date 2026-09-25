package com.ms.payment_service.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private Long productId;
    private Integer quantity;
    private Integer price;
    private Integer finalTotalPrice;
}
