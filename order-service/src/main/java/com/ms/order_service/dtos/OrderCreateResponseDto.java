package com.ms.order_service.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderCreateResponseDto {
    private Long id;
    private Long productId;
    private Integer quantity;
    private Integer price;
    private Integer finalTotalPrice;
    private String nextApiUrl;
    @JsonIgnore
    private Integer status;
}
