package com.online_store.logistic_service.api.shipper.exception;

public class ShipperAlreadyExistsException extends RuntimeException {
    public ShipperAlreadyExistsException(String message) {
        super(message);
    }
}