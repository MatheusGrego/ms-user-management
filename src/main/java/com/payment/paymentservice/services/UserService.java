package com.payment.paymentservice.services;

import com.payment.paymentservice.models.User;
import com.payment.paymentservice.models.dtos.UserRecordDto;
import com.payment.paymentservice.repositories.UserRepository;
import com.payment.paymentservice.services.interfaces.IUser;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements IUser<UserRecordDto> {
    final
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(UserRecordDto dto) {
        var user = new User();
        BeanUtils.copyProperties(dto, user);
        userRepository.save(user);
    }

    @Override
    public User findById(UUID id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }

}
