package com.payment.paymentservice.services.interfaces;

public interface Validator<T> {
    boolean validate(T object);
}
