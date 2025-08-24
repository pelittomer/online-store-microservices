package com.online_store.logistic_service.api.shipper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online_store.logistic_service.api.shipper.model.Shipper;
import java.util.Optional;

public interface ShipperRepository extends JpaRepository<Shipper, Long> {
    Boolean existsByName(String name);

    Boolean existsByEmail(String email);

    Optional<Shipper> findByApiKey(String apiKey);
}
