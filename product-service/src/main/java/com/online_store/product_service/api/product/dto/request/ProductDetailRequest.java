package com.online_store.product_service.api.product.dto.request;

import java.util.List;

import com.online_store.product_service.api.product.dto.base.FeatureDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

public record ProductDetailRequest(
        @Size(max = 2000, message = "Description cannot exceed 2000 characters.") String description,

        @Size(max = 500, message = "Short description cannot exceed 500 characters.") String shortDescription,

        @Valid List<FeatureDto> features,

        @Valid List<ProductCriteriaRequest> productCriterias) {

}
