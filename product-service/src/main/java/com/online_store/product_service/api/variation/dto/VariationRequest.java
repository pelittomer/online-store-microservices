package com.online_store.product_service.api.variation.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record VariationRequest(

        @NotBlank(message = "Variation name cannot be empty.") @Size(min = 2, max = 100, message = "Variation name must be between 2 and 100 characters.") String name,

        @NotNull(message = "Category cannot be null.") Long category,

        @NotEmpty(message = "Variation options cannot be empty.") @Size(min = 1, message = "At least one option must be provided for the variation.") List<@NotBlank(message = "Variation option name cannot be empty.") @Size(min = 1, max = 100, message = "Variation option name must be between 1 and 100 characters.") String> options) {

}
