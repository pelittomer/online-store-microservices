package com.online_store.cart_service.api.cart.dto.response;

public record CartItemResponse(
        Long id,
        Integer quantity,
        ProductResponse product,
        Long productStock) {

}
