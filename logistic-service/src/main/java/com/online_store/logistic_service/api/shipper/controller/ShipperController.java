package com.online_store.logistic_service.api.shipper.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.online_store.logistic_service.api.shipper.dto.ShipperRequest;
import com.online_store.logistic_service.api.shipper.dto.ShipperResponse;
import com.online_store.logistic_service.api.shipper.service.ShipperService;
import com.online_store.logistic_service.common.response.ApiResponse;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/shipper")
public class ShipperController {
    private final ShipperService service;

    public ShipperController(ShipperService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createShipper(
            @Valid @RequestPart("shipper") ShipperRequest shipperRequest,
            @RequestPart(value = "file", required = true) MultipartFile file) {
        return ResponseEntity.ok(
                ApiResponse.success("",
                        service.createShipper(shipperRequest, file)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ShipperResponse>>> listShippers() {
        return ResponseEntity.ok(
                ApiResponse.success("", service.listShippers()));
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<ShipperResponse>> getShipperById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success("", service.getShipperById(id)));
    }

}
