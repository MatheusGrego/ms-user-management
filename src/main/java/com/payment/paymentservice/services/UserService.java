package com.payment.paymentservice.services;

import com.payment.paymentservice.exceptions.UserNotFoundException;
import com.payment.paymentservice.models.Response;
import com.payment.paymentservice.models.User;
import com.payment.paymentservice.models.dtos.UserRecordDto;
import com.payment.paymentservice.models.factories.ResponseFactory;
import com.payment.paymentservice.repositories.UserRepository;
import com.payment.paymentservice.services.interfaces.IUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUser<UserRecordDto> {
    final
    UserRepository userRepository;
    final
    ResponseFactory responseFactory;

    public UserService(UserRepository userRepository, ResponseFactory responseFactory) {
        this.userRepository = userRepository;
        this.responseFactory = responseFactory;
    }

    @Override
    public Response save(UserRecordDto dto, HttpServletRequest request) {
        var user = new User();
        BeanUtils.copyProperties(dto, user);
        userRepository.save(user);

        return responseFactory.createCreatedResponse(request.getRequestURI(), "User successfully created");
    }

    @Override
    public User findById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Response update(UUID id, UserRecordDto dto, HttpServletRequest request) {
        User existingUser = findById(id);
        BeanUtils.copyProperties(dto, existingUser);
        userRepository.save(existingUser);

        return responseFactory.createSuccessResponse(request.getRequestURI(), "User with ID =%s successfully updated".formatted(id));
    }

    @Override
    public Response delete(UUID id, HttpServletRequest request) {
        User existingUser = findById(id);
        userRepository.deleteById(existingUser.getId());

        return responseFactory.createSuccessResponse(request.getRequestURI(), "User with ID =%s successfully deleted".formatted(id));
    }

}
