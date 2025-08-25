package com.online_store.product_service.api.brand.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.online_store.product_service.api.brand.dto.BrandRequest;
import com.online_store.product_service.api.brand.dto.BrandResponse;
import com.online_store.product_service.api.brand.service.BrandService;
import com.online_store.product_service.common.response.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/product/brand")
public class BrandController {
    private final BrandService service;

    public BrandController(BrandService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createBrand(
            @Valid @RequestPart("brand") BrandRequest brandRequestDto,
            @RequestPart(value = "file") MultipartFile file) {
        return ResponseEntity.ok(
                ApiResponse.success("",
                        service.createBrand(brandRequestDto, file)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BrandResponse>>> listBrands() {
        return ResponseEntity.ok(
                ApiResponse.success("",
                        service.listBrands()));
    }
}
