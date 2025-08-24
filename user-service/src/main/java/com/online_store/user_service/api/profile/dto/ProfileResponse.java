package com.online_store.user_service.api.profile.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.online_store.user_service.api.profile.model.Gender;

public record ProfileResponse(
        Long id,
        String firstName,
        String lastName,
        LocalDate birthOfDate,
        Gender gender,
        Long user,
        LocalDateTime updatedAt) {

}
