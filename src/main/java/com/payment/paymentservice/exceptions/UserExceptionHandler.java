package com.payment.paymentservice.exceptions;

import com.payment.paymentservice.models.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {


        Response response = new Response(
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining()),
                Instant.now()

        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<Response> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {


        Response response = new Response(
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.joining("; ")),
                Instant.now()

        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Response> handleDataIntegrityException(HttpServletRequest request) {


        Response response = new Response(
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                "Registry already exists",
                Instant.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}