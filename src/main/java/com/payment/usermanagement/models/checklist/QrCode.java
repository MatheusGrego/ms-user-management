package com.payment.usermanagement.models.checklist;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity(name = "QR_CODE")
@Data
public class QrCode {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String code;

    //TODO Checklist name
    private String region;

    @OneToOne(mappedBy = "qrCode", cascade = CascadeType.ALL)
    private Checklist checklist;
}
