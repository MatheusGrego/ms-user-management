package com.payment.paymentservice.services.interfaces;

import com.payment.paymentservice.models.base.User;

import java.util.List;
import java.util.UUID;

public interface IUser<T>{
    void save(T o);
    User findById(UUID id);
    List<User> findAll();
    void update();
    void delete();
}
