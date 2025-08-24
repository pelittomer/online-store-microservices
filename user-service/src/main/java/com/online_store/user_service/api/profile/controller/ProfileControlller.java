package com.online_store.user_service.api.profile.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online_store.user_service.api.profile.dto.ProfileRequest;
import com.online_store.user_service.api.profile.dto.ProfileResponse;
import com.online_store.user_service.api.profile.service.ProfileService;
import com.online_store.user_service.common.response.ApiResponse;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/user/profile")
public class ProfileControlller {
    private final ProfileService service;

    public ProfileControlller(ProfileService service) {
        this.service = service;
    }

    @PutMapping
    public ResponseEntity<ApiResponse<String>> updateProfile(@Valid @RequestBody ProfileRequest profileRequest) {
        return ResponseEntity.ok(
                ApiResponse.success("",
                        service.updateProfile(profileRequest)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfile() {
        return ResponseEntity.ok(
                ApiResponse.success("", service.getProfile()));
    }

}
