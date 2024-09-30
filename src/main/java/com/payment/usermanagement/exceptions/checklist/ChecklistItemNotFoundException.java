package com.payment.usermanagement.exceptions.checklist;

public class ChecklistItemNotFoundException extends RuntimeException {
    public ChecklistItemNotFoundException(String message) {
        super(message);
    }
}
