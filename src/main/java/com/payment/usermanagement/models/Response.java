package com.payment.usermanagement.models;

import java.time.Instant;

public record Response(String path, int status, String messages, Instant date) {

}
