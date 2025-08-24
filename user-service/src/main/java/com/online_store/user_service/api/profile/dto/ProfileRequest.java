package com.online_store.user_service.api.profile.dto;

import java.time.LocalDate;

import com.online_store.user_service.api.profile.model.Gender;

import jakarta.validation.constraints.Past;

public record ProfileRequest(
        String firstName,
        String lastName,
        Gender gender,
        @Past(message = "Birth of date must be in the past") LocalDate birthOfDate) {

}
