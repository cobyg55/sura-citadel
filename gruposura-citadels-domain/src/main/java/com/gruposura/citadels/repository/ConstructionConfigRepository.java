package com.gruposura.citadels.repository;

import com.gruposura.citadels.model.ConstructionConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConstructionConfigRepository extends JpaRepository<ConstructionConfig, Long> {
    Optional<ConstructionConfig> findByName(String name);
}
