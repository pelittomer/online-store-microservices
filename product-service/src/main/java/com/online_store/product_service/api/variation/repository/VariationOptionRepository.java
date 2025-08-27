package com.online_store.product_service.api.variation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online_store.product_service.api.variation.model.VariationOption;

public interface VariationOptionRepository extends JpaRepository<VariationOption, Long> {

}
