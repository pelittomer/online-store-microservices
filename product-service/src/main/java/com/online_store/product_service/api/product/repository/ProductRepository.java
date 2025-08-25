package com.online_store.product_service.api.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online_store.product_service.api.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
