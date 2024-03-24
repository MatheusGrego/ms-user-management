package com.payment.paymentservice.services.validators;

import com.payment.paymentservice.enums.DocumentType;
import com.payment.paymentservice.models.dtos.UserRecordDto;
import com.payment.paymentservice.services.interfaces.groups.CnpjGroup;
import com.payment.paymentservice.services.interfaces.groups.CpfGroup;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;

public class UserRecordDtoGroupSequenceProvider implements DefaultGroupSequenceProvider<UserRecordDto> {
    public List<Class<?>> getValidationGroups(UserRecordDto user) {
        List<Class<?>> groups = new ArrayList<>();
        groups.add(UserRecordDto.class);
        if (user != null) {
            if (DocumentType.CPF.equals(user.documentType())) {
                groups.add(CpfGroup.class);
            } else if (DocumentType.CNPJ.equals(user.documentType())) {
                groups.add(CnpjGroup.class);
            }
        }
        return groups;
    }
}

