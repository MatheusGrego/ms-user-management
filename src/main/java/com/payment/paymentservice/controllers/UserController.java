package com.payment.paymentservice.controllers;

import com.payment.paymentservice.models.Response;
import com.payment.paymentservice.models.dtos.UserRecordDto;
import com.payment.paymentservice.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    final
    UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<Response> saveUser(@RequestBody @Valid UserRecordDto userRecordDto, HttpServletRequest request) {
        userService.save(userRecordDto);
        Response response = new Response(request.getRequestURI(), HttpStatus.CREATED.value(), "User successfully created", Instant.now());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
