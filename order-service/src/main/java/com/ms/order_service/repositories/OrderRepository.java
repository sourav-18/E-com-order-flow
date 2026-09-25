package com.ms.order_service.repositories;

import com.ms.order_service.entities.OrderEntity;
import com.ms.order_service.entities.types.OrderStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
    Optional<OrderEntity> findByIdempotencyKey(String idempotencyKey);

    @Modifying
    @Query("""
            UPDATE OrderEntity
            SET status=:currentStatus
            WHERE status=:previousStatus AND id=:id
            """)
    Integer updateStatus(@Param("id") Long id,
                         @Param("currentStatus")OrderStatusType currentStatus,
                         @Param("previousStatus") OrderStatusType previousStatus);
}
