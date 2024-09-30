package com.payment.usermanagement.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.usermanagement.exceptions.user.UserNotFoundException;
import com.payment.usermanagement.models.Response;
import com.payment.usermanagement.models.User;
import com.payment.usermanagement.models.factories.ResponseFactory;
import com.payment.usermanagement.repositories.UserRepository;
import com.payment.usermanagement.services.interfaces.ICrud;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserService implements ICrud<Object, Object> {
    final
    UserRepository userRepository;
    final
    ResponseFactory responseFactory;
    final ObjectMapper objectMapper;


    public UserService(UserRepository userRepository, ResponseFactory responseFactory, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.responseFactory = responseFactory;
        this.objectMapper = objectMapper;
    }

    @Override
    public Response save(Object dto, HttpServletRequest request) {
        var user = new User();
        BeanUtils.copyProperties(dto, user);

        userRepository.save(user);

        return responseFactory.createCreatedResponse(request.getRequestURI(), "User successfully created");
    }

    @Override
    public User findById(Object id) {
        return userRepository.findById((UUID) id).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Response update(Object id, Object dto, Object request) {
        User existingUser = findById(id);
        BeanUtils.copyProperties(dto, Objects.requireNonNull(existingUser));
        var mapperTest = objectMapper.convertValue(request, HttpServletRequest.class);
        userRepository.save(existingUser);

        return responseFactory.createSuccessResponse(mapperTest.getRequestURI(), "User with ID =%s successfully updated".formatted(id));
    }

    @Override
    public Response delete(Object id, Object request) {
        User existingUser = findById(id);
        var mapperTest = objectMapper.convertValue(request, HttpServletRequest.class);
        userRepository.deleteById(Objects.requireNonNull(existingUser).getId());

        return responseFactory.createSuccessResponse(mapperTest.getRequestURI(), "User with ID =%s successfully deleted".formatted(id));
    }

}
