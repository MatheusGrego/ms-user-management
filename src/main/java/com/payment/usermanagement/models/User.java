package com.payment.usermanagement.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.payment.usermanagement.enums.DocumentType;
import com.payment.usermanagement.enums.Role;
import com.payment.usermanagement.models.checklist.Checklist;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Entity(name = "service_users")
@Data
public class User implements Serializable, UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    /* TODO
     * - Token, full_name
     * - Role-Based permissions session managements
     * */

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String username;
    @Column(name = "user_full_name", nullable = false)
    private String full_name;
    @Column(name = "company_name", nullable = false)
    @JsonProperty("company_name")
    private String companyName;
    @Column(unique = true)
    private String document;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @Column(name = "ps_hs", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String pwd;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @OneToMany(mappedBy = "userFinisher", fetch = FetchType.EAGER)
    private List<Checklist> checklistsFinished;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    // Security

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return pwd;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}


