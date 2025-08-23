package com.online_store.auth_service.api.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online_store.auth_service.api.user.dto.UserResponse;
import com.online_store.auth_service.api.user.service.UserService;
import com.online_store.auth_service.common.response.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser() {
        return ResponseEntity.ok(
                ApiResponse.success("", service.getCurrentUser()));
    }

}
