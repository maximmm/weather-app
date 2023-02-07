package com.sotnim.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.ResponseEntity.badRequest;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RestClientException.class)
    protected ResponseEntity<?> handleRestClientException(RestClientException exception) {
        return asBadRequest("Error during external call", exception);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleException(Exception exception) {
        return asBadRequest("Something went wrong", exception);
    }

    private ResponseEntity<?> asBadRequest(String bodyMessage, Exception exception) {
        log.error(exception.getMessage(), exception);
        return badRequest().body(bodyMessage);
    }
}