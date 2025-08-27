package com.online_store.product_service.api.product.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.online_store.product_service.api.product.dto.request.ProductRequest;
import com.online_store.product_service.api.product.dto.response.ProductDetailsResponse;
import com.online_store.product_service.api.product.dto.response.ProductResponse;
import com.online_store.product_service.api.product.dto.response.ProductStockInfo;
import com.online_store.product_service.api.product.service.ProductService;
import com.online_store.product_service.common.response.ApiResponse;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/product")
public class ProductController {
        private final ProductService service;

        public ProductController(ProductService service) {
                this.service = service;
        }

        @PostMapping
        public ResponseEntity<ApiResponse<String>> addProduct(
                        @Valid @RequestPart("product") ProductRequest productRequest,
                        MultipartHttpServletRequest request) {
                return ResponseEntity.ok(
                                ApiResponse.success("",
                                                service.addProduct(productRequest, request)));
        }

        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<String>> updateProduct(
                        @PathVariable Long id,
                        MultipartHttpServletRequest request) {
                return ResponseEntity.ok(
                                ApiResponse.success("",
                                                service.updateProduct()));
        }

        @GetMapping
        public ResponseEntity<ApiResponse<List<ProductResponse>>> listProducts() {
                return ResponseEntity.ok(
                                ApiResponse.success("",
                                                service.listProducts()));
        }

        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<ProductDetailsResponse>> getProductById(
                        @PathVariable(required = true) Long id) {
                return ResponseEntity.ok(
                                ApiResponse.success("",
                                                service.getProductById(id)));
        }

        @GetMapping("{productId}/stock/{stockId}")
        public ResponseEntity<ApiResponse<ProductStockInfo>> getProductStock(
                        @PathVariable("productId") Long productId,
                        @PathVariable("stockId") Long stockId) {
                return ResponseEntity.ok(
                                ApiResponse.success("", service.getProductStock(productId, stockId)));
        }

}
