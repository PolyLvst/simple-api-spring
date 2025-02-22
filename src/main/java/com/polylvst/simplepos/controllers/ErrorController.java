package com.polylvst.simplepos.controllers;

import com.polylvst.simplepos.domain.dtos.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
@Slf4j
public class ErrorController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception ex) {
        log.error("Caught exception ", ex);
        ApiErrorResponse error = ApiErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("An unexpected error occurred")
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Caught illegal argument exception ", ex);
        ApiErrorResponse error = ApiErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage()) // hati hati dengan message yang diberikan
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        log.error("Caught bad credentials exception ", ex);
        ApiErrorResponse error = ApiErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(ex.getMessage()) // hati hati dengan message yang diberikan
                .build();
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ApiErrorResponse> handleDisabledException(DisabledException ex) {
        log.error("Caught disabled exception ", ex);
        ApiErrorResponse error = ApiErrorResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .message(ex.getMessage()) // hati hati dengan message yang diberikan
                .build();
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }
}
