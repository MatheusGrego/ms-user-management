package com.payment.paymentservice.controllers;

import com.payment.paymentservice.dtos.UserRecordDto;
import com.payment.paymentservice.models.base.User;
import com.payment.paymentservice.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    final
    UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/api/v1/users")
    public ResponseEntity<User> saveUser(@RequestBody @Valid UserRecordDto userRecordDto) {
        var user = new User();
        BeanUtils.copyProperties(userRecordDto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(user));
    }
}
