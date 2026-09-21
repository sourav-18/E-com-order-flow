package com.ms.order_service.mapper;


import com.ms.order_service.dtos.OrderCreateResponseDto;
import com.ms.order_service.dtos.OrderCreateRequestDto;
import com.ms.order_service.dtos.OrderDto;
import com.ms.order_service.dtos.ProductDto;
import com.ms.order_service.entities.OrderEntity;

public class OrderMapper {
    public static OrderEntity toEntity(OrderCreateRequestDto orderCreateRequestDto,ProductDto productDto){
        return OrderEntity.builder()
                .productId(productDto.getId())
                .price(productDto.getPrice())
                .quantity(orderCreateRequestDto.getQuantity())
                .finalTotalPrice(productDto.getPrice()*orderCreateRequestDto.getQuantity())
                .idempotencyKey(orderCreateRequestDto.getIdempotencyKey())
                .build();
    }

    public static OrderDto toDto(OrderEntity order){
        return OrderDto.builder()
                .id(order.getId())
                .productId(order.getProductId())
                .price(order.getPrice())
                .quantity(order.getQuantity())
                .finalTotalPrice(order.getFinalTotalPrice())
                .build();
    }

    public static OrderCreateResponseDto toDto(OrderEntity order,Integer status){
        return OrderCreateResponseDto.builder()
                .id(order.getId())
                .productId(order.getProductId())
                .price(order.getPrice())
                .quantity(order.getQuantity())
                .finalTotalPrice(order.getFinalTotalPrice())
                .status(status)
                .build();
    }

    public static OrderCreateResponseDto toDto(OrderEntity order,Integer status, String nextApiUrl){
        OrderCreateResponseDto dto = toDto(order, status);
        dto.setNextApiUrl(nextApiUrl);
        return dto;
    }
}
