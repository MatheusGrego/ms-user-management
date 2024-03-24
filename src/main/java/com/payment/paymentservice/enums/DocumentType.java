package com.payment.paymentservice.enums;

import com.payment.paymentservice.services.interfaces.groups.CnpjGroup;
import com.payment.paymentservice.services.interfaces.groups.CpfGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DocumentType {
    CPF(CpfGroup.class),
    CNPJ(CnpjGroup.class);

    private final Class<?> group;

}
