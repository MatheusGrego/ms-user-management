package com.payment.usermanagement.models.response.factories;

import com.payment.usermanagement.models.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ResponseFactory {
    public Response createSuccessResponse(String uri, String message) {
        return new Response(uri, HttpStatus.OK.value(), message, Instant.now());
    }

    public Response createNotFoundResponse(String uri, String message) {
        return new Response(uri, HttpStatus.NOT_FOUND.value(), message, Instant.now());
    }

    public Response createCreatedResponse(String uri, String message) {
        return new Response(uri, HttpStatus.CREATED.value(), message, Instant.now());
    }

    public Response createBadRequestResponse(String uri, String message) {
        return new Response(uri, HttpStatus.BAD_REQUEST.value(), message, Instant.now());
    }

    public Response createServerErrorResponse(String uri, String message) {
        return new Response(uri, HttpStatus.INTERNAL_SERVER_ERROR.value(), message, Instant.now());
    }


}
