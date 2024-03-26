package com.payment.paymentservice.services.interfaces;

import com.payment.paymentservice.models.Response;
import com.payment.paymentservice.models.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.UUID;

public interface IUser<T> {
    Response save(T user, HttpServletRequest request);

    User findById(UUID id);

    List<User> findAll();

    Response update(UUID id, T user, HttpServletRequest request);

    void delete();
}
