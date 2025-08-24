package com.online_store.user_service.api.address.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressRequest(
        @NotBlank(message = "City cannot be blank") String city,

        @NotBlank(message = "District cannot be blank") String district,

        String neighborhood,

        @NotBlank(message = "Street cannot be blank") String street,

        @NotBlank(message = "Building number cannot be blank") @Size(max = 10, message = "Building number cannot exceed 10 characters") String buildingNumber,

        String doorNumber,

        @NotBlank(message = "Phone number cannot be blank") String phone

) {

}
