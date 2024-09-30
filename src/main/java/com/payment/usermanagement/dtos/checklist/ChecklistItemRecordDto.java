package com.payment.usermanagement.dtos.checklist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ChecklistItemRecordDto(
        String name,
        String description,
        boolean completed,
        @JsonProperty("image_url")
        String imageUrl,//  S3
        @JsonIgnore
                @JsonProperty("checklist_id")
        UUID checklistId
) {
}
