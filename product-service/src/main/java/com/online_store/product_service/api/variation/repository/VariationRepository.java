package com.online_store.product_service.api.variation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online_store.product_service.api.category.model.Category;
import com.online_store.product_service.api.variation.model.Variation;

public interface VariationRepository extends JpaRepository<Variation, Long> {
    List<Variation> findByCategoryIsNull();

    List<Variation> findByCategory(Category currentCategory);
}
