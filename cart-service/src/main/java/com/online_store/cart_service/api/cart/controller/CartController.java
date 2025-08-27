package com.online_store.cart_service.api.cart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online_store.cart_service.api.cart.dto.request.CartRequest;
import com.online_store.cart_service.api.cart.dto.request.UpdateCartRequest;
import com.online_store.cart_service.api.cart.dto.response.CartResponse;
import com.online_store.cart_service.api.cart.service.CartService;
import com.online_store.cart_service.config.response.ApiResponse;

@RestController
@RequestMapping("/api/cart")
public class CartController {
        private final CartService service;

        public CartController(CartService service) {
                this.service = service;
        }

        @PostMapping
        public ResponseEntity<ApiResponse<String>> addProductToCart(
                        @RequestBody CartRequest cartRequest) {
                return ResponseEntity.ok(
                                ApiResponse.success("",
                                                service.addProductToCart(cartRequest)));
        }

        @DeleteMapping("{id}")
        public ResponseEntity<ApiResponse<String>> removeProductFromCart(@PathVariable Long id) {
                return ResponseEntity.ok(
                                ApiResponse.success("",
                                                service.removeProductFromCart(id)));
        }

        @DeleteMapping("/all")
        public ResponseEntity<ApiResponse<String>> clearAllCartItems() {
                return ResponseEntity.ok(
                                ApiResponse.success("",
                                                service.clearAllCartItems()));
        }

        @GetMapping
        public ResponseEntity<ApiResponse<CartResponse>> listCartItems() {
                return ResponseEntity.ok(
                                ApiResponse.success("",
                                                service.listCartItems()));
        }

        @PutMapping
        public ResponseEntity<ApiResponse<String>> updateCartItem(
                        @RequestBody UpdateCartRequest cartRequestDto) {
                return ResponseEntity.ok(
                                ApiResponse.success("",
                                                service.updateCartItem(cartRequestDto)));
        }
}
