package com.payment.paymentservice.services;

import com.payment.paymentservice.exceptions.UserNotFoundException;
import com.payment.paymentservice.models.Response;
import com.payment.paymentservice.models.User;
import com.payment.paymentservice.models.dtos.UserRecordDto;
import com.payment.paymentservice.repositories.UserRepository;
import com.payment.paymentservice.services.interfaces.IUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUser<UserRecordDto> {
    final
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Response save(UserRecordDto dto, HttpServletRequest request) {
        var user = new User();
        BeanUtils.copyProperties(dto, user);
        Response response = new Response(request.getRequestURI(), HttpStatus.CREATED.value(), "User successfully created", Instant.now());
        userRepository.save(user);

        return response;


    }

    @Override
    public User findById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new UserNotFoundException("User not found with ID: "+ id));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }

}
