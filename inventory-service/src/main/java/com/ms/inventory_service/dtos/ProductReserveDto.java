package com.ms.inventory_service.dtos;

import com.ms.inventory_service.entities.types.ProductReservesStatusType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ProductReserveDto {
    private Long id;
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private ProductReservesStatusType status;
    private int totalAmount;
    private LocalDateTime createdAt;
}
