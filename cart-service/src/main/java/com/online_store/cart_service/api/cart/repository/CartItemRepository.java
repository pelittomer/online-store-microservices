package com.online_store.cart_service.api.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online_store.cart_service.api.cart.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
