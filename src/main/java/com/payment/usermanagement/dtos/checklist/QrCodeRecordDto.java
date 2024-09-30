package com.payment.usermanagement.dtos.checklist;

import java.util.UUID;

public record QrCodeRecordDto(
        String code,
        String region,
        UUID checklist
) {
}