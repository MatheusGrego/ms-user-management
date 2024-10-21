package com.payment.usermanagement.models.checklist;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.payment.usermanagement.models.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity(name = "CHECK_LIST")
@Data
public class Checklist {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    @OneToMany(mappedBy = "checklist", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ChecklistItem> items;

    @ManyToOne
    @JoinColumn(name = "finisher_user_id")
    @JsonProperty("user_finisher")
    private User userFinisher;

    @OneToOne
    @JoinColumn(name = "qr_code_id")
    @JsonProperty("qr_code_id")
    private QrCode qrCode;

}
