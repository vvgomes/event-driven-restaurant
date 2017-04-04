package com.restaurant.menu.command.api;

import org.axonframework.commandhandling.model.AggregateNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.notFound;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handle(IllegalArgumentException e) {
        return badRequest().body(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handle(IllegalStateException e) {
        return badRequest().body(e.getMessage());
    }

    @ExceptionHandler(AggregateNotFoundException.class)
    public ResponseEntity<String> handle(AggregateNotFoundException e) {
        return notFound().build();
    }
}
