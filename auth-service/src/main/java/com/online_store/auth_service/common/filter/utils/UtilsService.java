package com.online_store.auth_service.common.filter.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UtilsService {
    private final PasswordEncoder passwordEncoder;

    public UtilsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String hashedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
