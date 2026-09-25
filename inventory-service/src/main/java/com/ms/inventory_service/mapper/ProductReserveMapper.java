package com.ms.inventory_service.mapper;

import com.ms.inventory_service.dtos.ProductReserveCreateResponseDto;
import com.ms.inventory_service.dtos.ProductReserveDto;
import com.ms.inventory_service.entities.ProductEntity;
import com.ms.inventory_service.entities.ProductReservesEntity;
import com.ms.inventory_service.entities.types.ProductReservesStatusType;

public class ProductReserveMapper {
    public static ProductReservesEntity toEntity(Long orderId, Long productId, Integer quantity, Integer totalAmount) {
        return ProductReservesEntity.builder()
                .orderId(orderId)
                .productId(productId)
                .quantity(quantity)
                .status(ProductReservesStatusType.pending)
                .totalAmount(totalAmount)
                .build();
    }

    public static ProductReserveCreateResponseDto toDto(ProductReservesEntity entity, Integer amount, String nextApiUrl) {
        return ProductReserveCreateResponseDto.builder()
                .orderId(entity.getOrderId())
                .totalAmount(amount)
                .nextApiUrl(nextApiUrl)
                .build();
    }

    public static ProductReserveDto toDto(ProductReservesEntity entity) {
        return ProductReserveDto.builder()
                .id(entity.getId())
                .orderId(entity.getOrderId())
                .productId(entity.getProductId())
                .quantity(entity.getQuantity())
                .totalAmount(entity.getTotalAmount())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .build();

    }
}
