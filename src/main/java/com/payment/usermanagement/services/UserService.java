package com.payment.usermanagement.services;

import com.payment.usermanagement.controllers.UserController;
import com.payment.usermanagement.exceptions.UserNotFoundException;
import com.payment.usermanagement.models.User;
import com.payment.usermanagement.models.dtos.Response;
import com.payment.usermanagement.models.dtos.UserRecordDto;
import com.payment.usermanagement.models.factories.ResponseFactory;
import com.payment.usermanagement.repositories.UserRepository;
import com.payment.usermanagement.services.interfaces.IUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        user.add(linkTo(methodOn(UserController.class).findAll()).withRel("Users list"));

        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        users.forEach(user -> user.add(linkTo(methodOn(UserController.class).findById(user.getId())).withSelfRel()));

        return users;

    }

    @Override
    public Response update(UUID id, UserRecordDto dto, HttpServletRequest request) {
        User existingUser = findById(id);
        BeanUtils.copyProperties(dto, Objects.requireNonNull(existingUser));
        userRepository.save(existingUser);

        return responseFactory.createSuccessResponse(request.getRequestURI(), "User with ID =%s successfully updated".formatted(id));
    }

    @Override
    public Response delete(UUID id, HttpServletRequest request) {
        User existingUser = findById(id);
        userRepository.deleteById(Objects.requireNonNull(existingUser).getId());

        return responseFactory.createSuccessResponse(request.getRequestURI(), "User with ID =%s successfully deleted".formatted(id));
    }


}
