package com.ms.inventory_service.mapper;

import com.ms.inventory_service.dtos.ProductDto;
import com.ms.inventory_service.entities.ProductEntity;

public class ProductMapper {
    public static ProductDto toDto(ProductEntity entity){
        return  ProductDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .availableQuantity(entity.getAvailableQuantity())
                .build();
    }
}
