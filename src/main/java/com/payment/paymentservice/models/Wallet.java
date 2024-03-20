package com.payment.paymentservice.models;

import com.payment.paymentservice.models.base.User;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Timestamp last_activity;
    private BigDecimal amount;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


}
