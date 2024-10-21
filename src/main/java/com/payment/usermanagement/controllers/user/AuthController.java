package com.payment.usermanagement.controllers.user;

import com.payment.usermanagement.dtos.user.AuthRecordRequest;
import com.payment.usermanagement.dtos.user.UserRecordDto;
import com.payment.usermanagement.models.response.Response;
import com.payment.usermanagement.services.auth.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody UserRecordDto userRecordDto, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(userRecordDto, request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Response> authenticate(@RequestBody AuthRecordRequest authRequest, HttpServletRequest request  ) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.authenticate(authRequest, request));
    }
}
