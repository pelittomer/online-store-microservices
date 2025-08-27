package com.online_store.product_service.api.product.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.online_store.product_service.api.brand.dto.BrandResponse;
import com.online_store.product_service.api.category.dto.CategoryResponse;
import com.online_store.product_service.api.product.dto.base.DiscountDto;
import com.online_store.product_service.client.logistic.CompanyResponse;
import com.online_store.product_service.client.logistic.ShipperResponse;

public record ProductDetailsResponse(
                Long id,
                String name,
                DiscountDto discount,
                Boolean isPublished,
                List<Long> images,
                BrandResponse brand,
                ShipperResponse shipper,
                CompanyResponse company,
                CategoryResponse category,
                ProductDetailsDetailResponse productDetail,
                List<ProductStockResponse> productStocks,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {

}
