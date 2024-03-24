package com.payment.paymentservice.models;

import com.payment.paymentservice.enums.DocumentType;
import com.payment.paymentservice.enums.UserType;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Entity(name = "service_users")
@Data
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /* TODO
     * - Role-Based permissions, OAuth2 Tokens
     * - Notifications Preferences, Session managements, CEP
     * */

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String username;
    @Column(name = "user_full_name", nullable = false)
    private String full_name;
    @Column(unique = true)
    private String document;
    private BigDecimal balance;
    private String address;
    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;
    private String phone_number;
    @Column(name = "ps_hs", nullable = false)
    private String pwd;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();
    @Column(name = "updated_at")
    private Timestamp updatedAt;
}


