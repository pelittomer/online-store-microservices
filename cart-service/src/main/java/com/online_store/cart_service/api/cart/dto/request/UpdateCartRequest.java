package com.online_store.cart_service.api.cart.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateCartRequest(
        @NotNull(message = "Cart ID cannot be null.") @Min(value = 1, message = "Cart ID must be a positive number.") Long id,

        @NotNull(message = "Quantity cannot be null.") @Min(value = 1, message = "Quantity must be at least 1.") Integer quantity) {

}
