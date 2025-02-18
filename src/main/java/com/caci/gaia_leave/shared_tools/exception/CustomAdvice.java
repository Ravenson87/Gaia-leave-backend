package com.caci.gaia_leave.shared_tools.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RestControllerAdvice
public class CustomAdvice {

    /**
     * 404 default error message.
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorModel notFound(NoHandlerFoundException ex) {
        return buildErrorModel(
                HttpStatus.NOT_FOUND,
                ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString() + " not found"
        );
    }

    /**
     * MethodArgumentNotValidException.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorModel handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errorList = new LinkedList<>();
        if (ex.hasErrors()) {
            errorList = ex.getAllErrors().stream()
                    .map(err -> err.getDefaultMessage() != null ? err.getDefaultMessage() : "Validation error")
                    .toList();
        }
        String combinedErrors = errorList.isEmpty()
                ? "Argument not valid."
                : String.join(", ", errorList);

        return buildErrorModel(HttpStatus.BAD_REQUEST, combinedErrors);
    }

    /**
     * HttpMessageNotReadableException.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorModel handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return buildErrorModel(HttpStatus.BAD_REQUEST, "Message not readable. Bad request error");
    }

    /**
     * MissingServletRequestParameterException.
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorModel handleMissingParams(MissingServletRequestParameterException ex) {
        return buildErrorModel(HttpStatus.BAD_REQUEST, "Missing parameter: " + ex.getParameterName());
    }

    /**
     * NumberFormatException.
     */
    @ExceptionHandler(NumberFormatException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorModel handleNumberFormatException(NumberFormatException ex) {
        return buildErrorModel(HttpStatus.BAD_REQUEST, "Number format error: " + ex.getMessage());
    }

    /**
     * IllegalArgumentException.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorModel handleIllegalArgumentException(IllegalArgumentException ex) {
        return buildErrorModel(HttpStatus.BAD_REQUEST, "Illegal argument error: " + ex.getMessage());
    }

    /**
     * SQLException.
     */
    @ExceptionHandler(SQLException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // Po želji
    public ErrorModel handleSqlException(SQLException ex) {
        return buildErrorModel(HttpStatus.INTERNAL_SERVER_ERROR, "SQL error: " + ex.getMessage());
    }

    /**
     * Error model.
     */
    private ErrorModel buildErrorModel(HttpStatus status, String message) {
        String url = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
        ErrorModel errorModel = new ErrorModel();
        errorModel.setTimestamp(new Date());
        errorModel.setStatus(status.value());
        errorModel.setError(status.getReasonPhrase());
        errorModel.setMessage(message);
        errorModel.setPath(url);
        return errorModel;
    }
}
