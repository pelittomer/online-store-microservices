package com.online_store.product_service.api.product.dto.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ProductCriteriaRequest(
        @NotNull(message = "Variation ID cannot be null.") @Min(value = 1, message = "Variation ID must be a positive number.") Long variation,

        @Valid List<CriteriaOptionRequest> criteriaOptions) {

}
