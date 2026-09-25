package com.ms.inventory_service.repositories;

import com.ms.inventory_service.entities.ProductReservesEntity;
import com.ms.inventory_service.entities.types.ProductReservesStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductReserveRepository extends JpaRepository<ProductReservesEntity,Long> {
    Optional<ProductReservesEntity> findByOrderId(Long id);

    @Modifying
    @Query("""
            UPDATE ProductReservesEntity
            SET status=:currentStatus
            WHERE status=:previousStatus AND id=:id
            """)
    Integer updateStatus(@Param("id") Long id,
                         @Param("currentStatus") ProductReservesStatusType currentStatus,
                         @Param("previousStatus") ProductReservesStatusType previousStatus);
}
