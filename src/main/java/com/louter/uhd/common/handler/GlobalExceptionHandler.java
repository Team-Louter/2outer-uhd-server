package com.louter.uhd.common.handler;

import com.louter.uhd.common.exception.*;
import com.louter.uhd.common.exception.IllegalArgumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyUsingException.class)
    public ResponseEntity<Object> handleAlreadyUsing(AlreadyUsingException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)    // 409
                .body(Map.of(
                        "success", false,
                        "error_name", "AlreadyUsingIdException",
                        "message", e.getMessage()
                ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgs(IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // 400
                .body(Map.of(
                        "success", false,
                        "error_name", "IllegalArgumentException",
                        "message", e.getMessage()
                ));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFound(NotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)   // 404
                .body(Map.of(
                        "success", false,
                        "error_name", "NotFoundException",
                        "message", e.getMessage()
                ));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthentication(AuthenticationException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)     // 401
                .body(Map.of(
                        "success", false,
                        "error_name", "AuthenticationException",
                        "message", e.getMessage()
                ));
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<Object> handleTokenExpired(TokenExpiredException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)     // 401
                .body(Map.of(
                        "success", false,
                        "error_name", "TokenExpiredException",
                        "message", e.getMessage()
                ));
    }

    @ExceptionHandler(WrongVerifiedException.class)
    public ResponseEntity<Object> handleWrongVerified(WrongVerifiedException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)     // 401
                .body(Map.of(
                        "success", false,
                        "error_name", "WrongVerifiedException",
                        "message", e.getMessage()
                ));
    }
}
