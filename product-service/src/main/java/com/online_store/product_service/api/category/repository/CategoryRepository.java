package com.online_store.product_service.api.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online_store.product_service.api.category.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
