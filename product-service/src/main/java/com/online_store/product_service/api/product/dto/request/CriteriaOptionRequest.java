package com.online_store.product_service.api.product.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CriteriaOptionRequest(
        @NotNull(message = "Variation option ID cannot be null.") @Min(value = 1, message = "Variation option ID must be a positive number.") Long variationOption) {

}
