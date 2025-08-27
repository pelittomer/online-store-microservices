package com.online_store.product_service.api.category.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.online_store.product_service.api.category.dto.CategoryRequest;
import com.online_store.product_service.api.category.dto.CategoryResponse;
import com.online_store.product_service.api.category.dto.CategoryTreeResponse;
import com.online_store.product_service.api.category.service.CategoryService;
import com.online_store.product_service.common.response.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/product/category")
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createCategory(
            @Valid @RequestPart("category") CategoryRequest categoryRequest,
            @RequestPart(value = "image", required = true) MultipartFile imageFile,
            @RequestPart(value = "icon", required = true) MultipartFile iconFile) {
        return ResponseEntity.ok(
                ApiResponse.success("",
                        service.createCategory(categoryRequest, imageFile, iconFile)));
    }

    @GetMapping("/leafs")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> listLeafCategories() {
        return ResponseEntity.ok(
                ApiResponse.success("",
                        service.listLeafCategories()));
    }

    @GetMapping("/roots")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> listRootCategories() {
        return ResponseEntity.ok(
                ApiResponse.success("",
                        service.listLeafCategories()));
    }

    @GetMapping("/tree")
    public ResponseEntity<ApiResponse<List<CategoryTreeResponse>>> getCategoryTree() {
        return ResponseEntity.ok(
                ApiResponse.success("",
                        service.getCategoryTree()));
    }
}
