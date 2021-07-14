package com.gruposura.citadels.repository;

import com.gruposura.citadels.model.ConstructionOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ConstructionOrderRepository extends JpaRepository<ConstructionOrder, Long> {
    Optional<ConstructionOrder> findByCode(String code);
    Optional<ConstructionOrder> findByLatitudeAndLongitude(String latitude, String longitude);
    @Query(value = "SELECT * FROM CONSTRUCTION_ORDER ORDER BY CREATED_AT DESC LIMIT 1", nativeQuery = true)
    Optional<ConstructionOrder> findLastOrder();
}
