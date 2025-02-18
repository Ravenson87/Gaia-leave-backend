package com.caci.gaia_leave.shared_tools.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.context.MessageSourceAware;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

public interface CustomGlobalExceptionHandlerIpl extends MessageSourceAware {
    // error handle for @Valid
    ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    );

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<Object> handleConstraintViolationException(
            ConstraintViolationException ex
    );
}