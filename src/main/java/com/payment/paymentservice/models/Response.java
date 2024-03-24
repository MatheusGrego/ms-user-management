package com.payment.paymentservice.models;

import java.time.Instant;

public record Response(
        String path,
        int status,
        String messages,
        Instant date
) {


}
