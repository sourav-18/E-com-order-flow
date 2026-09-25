package com.ms.payment_service.repositories;

import com.ms.payment_service.entities.PaymentEntity;
import com.ms.payment_service.entities.types.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity,Long> {
    Optional<PaymentEntity> findByOrderId(Long orderId);

    @Modifying
    @Query("""
            UPDATE PaymentEntity
            SET status=:currentStatus
            WHERE status=:previousStatus AND id=:id
            """)
    Integer updateStatus(@Param("id") Long id,
                         @Param("currentStatus") PaymentStatus currentStatus,
                         @Param("previousStatus") PaymentStatus previousStatus);
}
