package com.ms.payment_service.dtos;


import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductReserveDto {
    private Long id;
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private ProductReservesStatusType status;
    private int totalAmount;
    private LocalDateTime createdAt;
}
