package com.payment.paymentservice.models;

import com.payment.paymentservice.models.base.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class CommonUser extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


}
