package com.online_store.logistic_service.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.online_store.logistic_service.api.company.exception.CompanyAlreadyExistsException;
import com.online_store.logistic_service.api.company.exception.CompanyNotFoundException;
import com.online_store.logistic_service.api.company.exception.CompanyUpdateException;
import com.online_store.logistic_service.api.shipper.exception.ShipperAlreadyExistsException;
import com.online_store.logistic_service.api.shipper.exception.ShipperNotFoundException;
import com.online_store.logistic_service.common.response.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCompanyNotFoundException(CompanyNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CompanyAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCompanyAlreadyExistsException(CompanyAlreadyExistsException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value()),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CompanyUpdateException.class)
    public ResponseEntity<ErrorResponse> handleCompanyUpdateException(CompanyUpdateException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ShipperAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleShipperAlreadyExistsException(ShipperAlreadyExistsException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value()),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ShipperNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleShipperNotFoundExceptionException(ShipperNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value()),
                HttpStatus.NOT_FOUND);
    }
}
