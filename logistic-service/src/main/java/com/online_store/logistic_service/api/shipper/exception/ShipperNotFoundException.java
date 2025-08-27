package com.online_store.logistic_service.api.shipper.exception;

public class ShipperNotFoundException extends RuntimeException {
    public ShipperNotFoundException(String message) {
        super(message);
    }
}