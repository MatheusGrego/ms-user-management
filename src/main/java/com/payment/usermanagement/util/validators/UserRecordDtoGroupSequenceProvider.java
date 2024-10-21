package com.payment.usermanagement.util.validators;

import com.payment.usermanagement.enums.DocumentType;
import com.payment.usermanagement.dtos.user.UserRecordDto;
import com.payment.usermanagement.util.validators.groups.CnpjGroup;
import com.payment.usermanagement.util.validators.groups.CpfGroup;
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

