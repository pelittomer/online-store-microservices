package com.online_store.product_service.api.brand.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BrandRequest(
        @NotBlank(message = "Brand name cannot be blank") @Size(min = 2, max = 100, message = "Brand name must be between 2 and 100 characters") String name) {

}
