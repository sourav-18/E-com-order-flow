package com.ms.order_service.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateResponseDto {
    private Long id;
    private Long productId;
    private Long userId;
    private Integer quantity;
    private Integer price;
    private Integer finalTotalPrice;
    private String nextApiUrl;
    @JsonIgnore
    private Integer status;
}
