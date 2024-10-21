package com.payment.usermanagement.enums;

import com.payment.usermanagement.util.validators.groups.CnpjGroup;
import com.payment.usermanagement.util.validators.groups.CpfGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DocumentType {
    CPF(CpfGroup.class),
    CNPJ(CnpjGroup.class);

    private final Class<?> group;

}
