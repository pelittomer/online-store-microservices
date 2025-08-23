package com.online_store.auth_service.api.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @NotBlank(message = "Email cannot be empty") String email,
        @NotBlank(message = "Password cannot be empty") String password) {

}
