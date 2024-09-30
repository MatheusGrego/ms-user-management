package com.payment.usermanagement.exceptions.checklist;

import com.payment.usermanagement.models.Response;
import com.payment.usermanagement.models.factories.ResponseFactory;
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

import java.util.stream.Collectors;

@RestControllerAdvice
public class ChecklistItemExceptionHandler {

    final ResponseFactory responseFactory;

    public ChecklistItemExceptionHandler(ResponseFactory responseFactory) {
        this.responseFactory = responseFactory;
    }

    // Exceção para validar violações de campos
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        var messages = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining());

        var response = responseFactory.createBadRequestResponse(request.getRequestURI(), messages);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Exceção para validação de argumentos inválidos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        var messages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));

        var response = responseFactory.createBadRequestResponse(request.getRequestURI(), messages);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Exceção para integridade de dados, como tentativa de duplicar registros
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Response> handleDataIntegrityViolationException(HttpServletRequest request) {
        var response = responseFactory.createBadRequestResponse(request.getRequestURI(), "Checklist item already exists");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Exceção para item não encontrado
    @ExceptionHandler(ChecklistItemNotFoundException.class)
    public ResponseEntity<Response> handleChecklistItemNotFoundException(ChecklistItemNotFoundException ex, HttpServletRequest request) {
        var response = responseFactory.createNotFoundResponse(request.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
