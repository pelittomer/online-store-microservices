package com.online_store.auth_service.api.user.dto;

import com.online_store.auth_service.api.user.model.Role;

public record UserResponse(Long id, String email, Role role) {

}
