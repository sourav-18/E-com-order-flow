package com.ms.inventory_service.repositories;

import com.ms.inventory_service.entities.ProductEntity;
import com.ms.inventory_service.entities.ProductReservesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {

    @Modifying
    @Query("""
            UPDATE ProductEntity
            SET availableQuantity=availableQuantity+:quantity
            WHERE id=:id
            """)
    Integer updateAvailableQuantity(@Param("id") Long id,
            @Param("quantity") Integer quantity);
}
