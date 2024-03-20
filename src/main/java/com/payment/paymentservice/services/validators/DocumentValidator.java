package com.payment.paymentservice.services.validators;

import com.payment.paymentservice.enums.DocumentType;
import com.payment.paymentservice.services.interfaces.Validator;

public class DocumentValidator implements Validator<DocumentType> {
    @Override
    public boolean validate(DocumentType object) {
        return false;
    }
}
