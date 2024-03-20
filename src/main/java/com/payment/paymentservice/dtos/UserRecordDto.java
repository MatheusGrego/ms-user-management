package com.payment.paymentservice.dtos;

public record UserRecordDto(String username, String full_name, String document,
                            String address, String email, String phone_number,
                            String pwd) {
}

