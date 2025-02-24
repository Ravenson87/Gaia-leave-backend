package com.caci.gaia_leave.shared_tools.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS)
public class TokenExpired extends RuntimeException {
    public TokenExpired(String message) {
        super(message);
    }
}