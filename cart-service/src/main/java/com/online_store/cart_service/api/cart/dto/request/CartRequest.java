package com.online_store.cart_service.api.cart.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CartRequest(
        @NotNull(message = "Quantity cannot be null.") @Min(value = 1, message = "Quantity must be at least 1.") Integer quantity,
        @NotNull(message = "Product ID cannot be null.") @Min(value = 1, message = "Product ID must be a positive number.") Long product,

        @NotNull(message = "Product Stock ID cannot be null.") @Min(value = 1, message = "Product Stock ID must be a positive number.") Long productStock) {

    public static CartRequest withDefaultQuantity(Long product, Long productStock) {
        return new CartRequest(1, product, productStock);
    }
}
