package com.ms.order_service.mapper;


import com.ms.order_service.dtos.OrderCreateRequestDto;
import com.ms.order_service.dtos.ProductDto;
import com.ms.order_service.entities.OrderEntity;

public class OrderMapper {
    public static OrderEntity toEntity(OrderCreateRequestDto orderCreateRequestDto,ProductDto productDto){
        return OrderEntity.builder()
                .productId(productDto.getId())
                .price(productDto.getPrice())
                .quantity(orderCreateRequestDto.getQuantity())
                .finalTotalPrice(productDto.getPrice()*orderCreateRequestDto.getQuantity())
                .build();
    }
}
