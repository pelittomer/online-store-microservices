package com.online_store.product_service.api.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
        @NotBlank(message = "Category name cannot be empty.") @Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters.") String name,

        @NotBlank(message = "Category description cannot be empty.") @Size(min = 10, max = 1000, message = "Category description must be between 10 and 1000 characters.") String description,

        Long parent) {

}
