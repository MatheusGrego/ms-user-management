package com.payment.paymentservice.controllers;

import com.payment.paymentservice.models.Response;
import com.payment.paymentservice.models.User;
import com.payment.paymentservice.models.dtos.UserRecordDto;
import com.payment.paymentservice.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController {
    final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<Response> save(@RequestBody @Valid UserRecordDto userRecordDto, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userRecordDto, request));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> findById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    @GetMapping("/users/")
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Response> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid UserRecordDto userRecordDto, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(id, userRecordDto, request));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Response> delete(@PathVariable(value = "id") UUID id, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.delete(id, request));
    }




}
