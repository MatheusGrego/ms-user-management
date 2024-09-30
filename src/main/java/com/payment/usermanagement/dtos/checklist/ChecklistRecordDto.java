package com.payment.usermanagement.dtos.checklist;

import jakarta.validation.constraints.NotNull;

import java.util.List;


public record ChecklistRecordDto(
        @NotNull
        String description,
        List<ChecklistItemRecordDto> items
) {
}
