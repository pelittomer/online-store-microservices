package com.online_store.auth_service.api.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online_store.auth_service.api.auth.dto.AuthRequest;
import com.online_store.auth_service.api.auth.service.AuthService;
import com.online_store.auth_service.api.user.model.Role;
import com.online_store.auth_service.common.response.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/user/auth")
public class AuthController {
    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("{role}/sign-up")
    public ResponseEntity<ApiResponse<String>> signUp(
            @Valid @RequestBody AuthRequest authRequest,
            @PathVariable Role role) {
        return ResponseEntity.ok(
                ApiResponse.success("",
                        service.register(authRequest, role)));
    }

    @PostMapping("{role}/sign-in")
    public ResponseEntity<ApiResponse<String>> signIn(
            @Valid @RequestBody AuthRequest authRequest,
            @PathVariable Role role,
            HttpServletResponse response) {
        return ResponseEntity.ok(
                ApiResponse.success("",
                        service.login(authRequest, role, response)));
    }

    @PostMapping("/sign-out")
    public ResponseEntity<ApiResponse<String>> signOut(
            HttpServletResponse response) {
        return ResponseEntity.ok(
                ApiResponse.success("",
                        service.logout(response)));
    }

    @GetMapping("/refresh")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }

}
