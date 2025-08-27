package com.online_store.cart_service.api.cart.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record CartResponse(
        Long id,
        List<CartItemResponse> cartItems,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {

}
