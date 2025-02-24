package com.caci.gaia_leave.shared_tools.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class BadToken extends RuntimeException {
    public BadToken(String message) {
        super("Invalid Token: " + message);
    }
}
