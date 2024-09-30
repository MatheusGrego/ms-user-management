package com.payment.usermanagement.models.checklist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;


@Entity(name = "CHECK_LIST_ITEM")
@Data
public class ChecklistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String description;
    private boolean completed;

    private String imagemUrl; //  S3

    @ManyToOne
    @JoinColumn(name = "checklist_id")
    @JsonIgnore
    private Checklist checklist;
}
