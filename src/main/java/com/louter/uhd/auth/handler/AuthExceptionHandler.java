package com.louter.uhd.auth.handler;

import com.louter.uhd.auth.exception.AlreadyUsingAccountException;
import com.louter.uhd.auth.exception.AuthenticationException;
import com.louter.uhd.auth.exception.IllegalArgsException;
import com.louter.uhd.auth.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class AuthExceptionHandler {
    @ExceptionHandler(AlreadyUsingAccountException.class)
    public ResponseEntity<Object> handleAlreadyUsingAccountException(AlreadyUsingAccountException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "success", false,
                        "error_name", "AlreadyUsingAccountException",
                        "message", e.getMessage()
                ));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "success", false,
                        "error_name", "AuthenticationException",
                        "message", e.getMessage()
                ));
    }

    @ExceptionHandler(IllegalArgsException.class)
    public ResponseEntity<Object> handleIllegalArgsException(IllegalArgsException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "success", false,
                        "error_name", "IllegalArgsException",
                        "message", e.getMessage()
                ));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "success", false,
                        "error_name", "UserNotFoundException",
                        "message", e.getMessage()
                ));
    }
}
