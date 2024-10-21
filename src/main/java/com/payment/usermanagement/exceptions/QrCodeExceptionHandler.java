package com.payment.usermanagement.exceptions;

import com.payment.usermanagement.models.response.Response;
import com.payment.usermanagement.models.response.factories.ResponseFactory;
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
public class QrCodeExceptionHandler {

    final ResponseFactory responseFactory;

    public QrCodeExceptionHandler(ResponseFactory responseFactory) {
        this.responseFactory = responseFactory;
    }

    // Exceção para violações de restrição de campos
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        var messages = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("; "));

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

    // Exceção para integridade de dados, como tentativa de duplicação de QR Code
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Response> handleDataIntegrityViolationException(HttpServletRequest request) {
        var response = responseFactory.createBadRequestResponse(request.getRequestURI(), "QR Code already exists");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Exceção para QR Code não encontrado
    @ExceptionHandler(QrCodeNotFoundException.class)
    public ResponseEntity<Response> handleQrCodeNotFoundException(QrCodeNotFoundException ex, HttpServletRequest request) {
        var response = responseFactory.createNotFoundResponse(request.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // Exceção para falha na geração do QR Code
    @ExceptionHandler(QrCodeGenerationException.class)
    public ResponseEntity<Response> handleQrCodeGenerationException(QrCodeGenerationException ex, HttpServletRequest request) {
        var response = responseFactory.createServerErrorResponse(request.getRequestURI(), "Failed to generate QR Code: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
