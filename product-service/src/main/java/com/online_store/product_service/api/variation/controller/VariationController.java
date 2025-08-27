package com.online_store.product_service.api.variation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.online_store.product_service.api.variation.dto.VariationRequest;
import com.online_store.product_service.api.variation.dto.VariationResponse;
import com.online_store.product_service.api.variation.service.VariationService;
import com.online_store.product_service.common.response.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/product/variation")
public class VariationController {
    private final VariationService service;

    public VariationController(VariationService service){
        this.service=service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> addVariation(
            @Valid @RequestBody VariationRequest variationRequest) {
        return ResponseEntity.ok(
                ApiResponse.success("",
                        service.addVariation(variationRequest)));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<VariationResponse>>> listVariations(
            @RequestParam Long categoryId) {
        return ResponseEntity.ok(
                ApiResponse.success("",
                        service.listVariations(categoryId)));
    }
}
