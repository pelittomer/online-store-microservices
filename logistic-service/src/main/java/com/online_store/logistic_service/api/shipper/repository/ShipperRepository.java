package com.online_store.logistic_service.api.shipper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online_store.logistic_service.api.shipper.model.Shipper;

public interface ShipperRepository extends JpaRepository<Shipper, Long> {

}
