package com.payment.paymentservice.models.base;

import com.payment.paymentservice.enums.DocumentType;
import com.payment.paymentservice.enums.UserType;
import com.payment.paymentservice.models.Transaction;
import com.payment.paymentservice.models.Wallet;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity(name = "common_user")
@Data
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /* TODO
     * - Role-Based permissions, OAuth Tokens
     * - Notifications Preferences, Session managements
     * */

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String username;
    @Column(name = "user_full_name", nullable = false)
    private String full_name;

    @Column(name = "document", nullable = false, length = 100, unique = true)
    private String document;

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

    @OneToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Transaction> transactions;


}
