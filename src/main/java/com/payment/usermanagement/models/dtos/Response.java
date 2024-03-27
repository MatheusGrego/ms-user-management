package com.payment.usermanagement.models.dtos;

import java.time.Instant;

public record Response(String path, int status, String messages, Instant date) {

}
