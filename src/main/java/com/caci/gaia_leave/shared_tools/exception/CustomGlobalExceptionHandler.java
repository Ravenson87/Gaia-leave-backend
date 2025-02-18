package com.caci.gaia_leave.shared_tools.exception;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@EnableWebMvc
@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler implements CustomGlobalExceptionHandlerIpl {
    // error handle for @Valid
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {

        ErrorModel errorModel = new ErrorModel();
        errorModel.setTimestamp(new Date());
        errorModel.setStatus(status.value());
        errorModel.setError(status.getReasonPhrase());
        errorModel.setMessage(ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
        errorModel.setPath(ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString());

        return new ResponseEntity<>(errorModel, headers, status);
    }

    /**
     * error handle for @Valid @RequestParam.
     *
     * @param ex ConstraintViolationException
     * @return Object
     */
    @Override
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(
            ConstraintViolationException ex
    ) {
        String message = "";
        List<String> errorList = new LinkedList<>();
        ex.getConstraintViolations().forEach(arg -> errorList.add(arg.getMessage()));
        message += errorList.get(0);
        ErrorModel errorModel = new ErrorModel();
        errorModel.setTimestamp(new Date());
        errorModel.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        errorModel.setError(String.valueOf(HttpStatus.BAD_REQUEST));
        errorModel.setMessage(message);
        errorModel.setPath(ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString());
        return ResponseEntity.badRequest().body(errorModel);
    }
}
