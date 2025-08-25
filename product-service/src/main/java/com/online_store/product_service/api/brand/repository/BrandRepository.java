package com.online_store.product_service.api.brand.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online_store.product_service.api.brand.model.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {

}
