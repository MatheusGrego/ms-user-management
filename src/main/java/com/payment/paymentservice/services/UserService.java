package com.payment.paymentservice.services;

import com.payment.paymentservice.dtos.UserRecordDto;
import com.payment.paymentservice.models.base.User;
import com.payment.paymentservice.repositories.UserRepository;
import com.payment.paymentservice.services.interfaces.IUser;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.UUID;

public class UserService implements IUser<UserRecordDto> {
    UserRepository userRepository;

    @Override
    public void save(UserRecordDto dto) {
        var user = new User();

        BeanUtils.copyProperties(dto, user);



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

    //--
}
