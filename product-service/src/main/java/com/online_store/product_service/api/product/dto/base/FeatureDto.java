package com.online_store.product_service.api.product.dto.base;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FeatureDto(
        @NotBlank(message = "Feature name cannot be blank.") @Size(max = 100, message = "Feature name cannot exceed 100 characters.") String name,

        @NotBlank(message = "Feature value cannot be blank.") @Size(max = 255, message = "Feature value cannot exceed 255 characters.") String value) {

}
