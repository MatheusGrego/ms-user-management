package com.payment.usermanagement.services.interfaces;

import jakarta.servlet.http.HttpServletRequest;

public interface ICrud<P, R> {
    R save(P entity, HttpServletRequest request);

    R findById(P id);

    R findAll();

    R update(P id, P entity, P request);

    R delete(P id, P request);

}
