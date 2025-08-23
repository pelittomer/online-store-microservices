package com.online_store.auth_service.common.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(String message, List<String> errors, int status) {
    public ErrorResponse(String message, int status) {
        this(message, null, status);
    }

    public ErrorResponse(List<String> errors, int status) {
        this("Validation failed.", errors, status);
    }
}
