package com.payment.usermanagement.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.payment.usermanagement.enums.DocumentType;
import com.payment.usermanagement.enums.UserType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;


@EqualsAndHashCode(callSuper = true)
@Entity(name = "service_users")
@Data
public class User extends RepresentationModel<User> implements Serializable {
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
    private BigDecimal balance = BigDecimal.ZERO;
    private String address;
    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;
    private String phone_number;
    @Column(name = "ps_hs", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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


