package com.online_store.user_service.api.address.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online_store.user_service.api.address.dto.AddressRequest;
import com.online_store.user_service.api.address.dto.AddressResponse;
import com.online_store.user_service.api.address.service.AddressService;
import com.online_store.user_service.common.response.ApiResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/user/address")
public class AddressController {
    private final AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createAddress(@RequestBody AddressRequest addressRequest) {
        return ResponseEntity.ok(
                ApiResponse.success("",
                        service.createAddress(addressRequest)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AddressResponse>>> listAddresses() {
        return ResponseEntity.ok(
                ApiResponse.success("",
                        service.listAddresses()));
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<AddressResponse>> getAddress(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success("",
                        service.getAddress(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteAddress(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success("",
                        service.deleteAddress(id)));
    }
}
