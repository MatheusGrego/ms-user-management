package com.payment.paymentservice.models.factories;

import com.payment.paymentservice.models.Response;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ResponseFactory {
    public Response createSuccessResponse(String uri, String message){
        return new Response(uri, HttpStatus.OK.value(), message, Instant.now());
    }
    public Response createNotFoundResponse(String uri, String message){
        return new Response(uri, HttpStatus.NOT_FOUND.value(), message, Instant.now());
    }

    public Response createCreatedResponse(String uri, String message) {
        return new Response(uri, HttpStatus.CREATED.value(), message, Instant.now());
    }
    public Response createBadRequestResponse(String uri, String message) {
        return new Response(uri, HttpStatus.BAD_REQUEST.value(), message, Instant.now());
    }
}
