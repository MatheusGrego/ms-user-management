package com.payment.usermanagement.models.response.factories;

import com.payment.usermanagement.models.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class AuthResponseFactory {

    private final ResponseFactory responseFactory;

    public AuthResponseFactory(ResponseFactory responseFactory) {
        this.responseFactory = responseFactory;
    }

    public Response createUnauthorizedResponse(String uri, String message) {
        return responseFactory.createBadRequestResponse(uri, message);
    }

    public Response createAuthenticationFailedResponse(String uri, String message) {
        return responseFactory.createBadRequestResponse(uri, message);
    }

    public Response createAccessDeniedResponse(String uri, String message) {
        return new Response(uri, HttpStatus.FORBIDDEN.value(), message, Instant.now());
    }

    public Response createSessionExpiredResponse(String uri, String message) {
        return new Response(uri, HttpStatus.UNAUTHORIZED.value(), message, Instant.now());
    }

    public Response createTokenExpiredResponse(String uri, String message) {
        return new Response(uri, HttpStatus.UNAUTHORIZED.value(), message, Instant.now());
    }
}
