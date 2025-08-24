package com.online_store.user_service.api.address.dto;

public record AddressResponse(
        Long id,
        String city,
        String district,
        String neighborhood,
        String street,
        String buildingNumber,
        String doorNumber,
        String phone) {

}
