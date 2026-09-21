package com.ms.inventory_service.mapper;

import com.ms.inventory_service.dtos.ProductReserveCreateResponseDto;
import com.ms.inventory_service.entities.ProductEntity;
import com.ms.inventory_service.entities.ProductReservesEntity;
import com.ms.inventory_service.entities.types.ProductReservesStatusType;

public class ProductReserveMapper {
    public static ProductReservesEntity toEntity(Long orderId,Long productId,Integer quantity){
       return ProductReservesEntity.builder()
               .orderId(orderId)
               .productId(productId)
               .quantity(quantity)
               .status(ProductReservesStatusType.pending)
               .build();
    }

    public static ProductReserveCreateResponseDto toDto(ProductReservesEntity entity,Integer amount,String nextApiUrl){
        return ProductReserveCreateResponseDto.builder()
                .orderId(entity.getOrderId())
                .amount(amount)
                .nextApiUrl(nextApiUrl)
                .build();
    }
}
