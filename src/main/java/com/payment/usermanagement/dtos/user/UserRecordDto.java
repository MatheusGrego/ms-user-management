package com.payment.usermanagement.dtos.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.payment.usermanagement.enums.DocumentType;
import com.payment.usermanagement.enums.UserType;
import com.payment.usermanagement.services.interfaces.groups.CnpjGroup;
import com.payment.usermanagement.services.interfaces.groups.CpfGroup;
import com.payment.usermanagement.services.validators.UserRecordDtoGroupSequenceProvider;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;

@GroupSequenceProvider(UserRecordDtoGroupSequenceProvider.class)
public record UserRecordDto(
        @NotBlank(message = "Username is required")
        String username,
        @NotBlank(message = "Full name is required")
        String full_name,
        @JsonProperty("company_name")
        @NotBlank(message = "Company name is required")
        String companyName,
        @NotBlank(message = "Document is required")
        @CPF(groups = CpfGroup.class)
        @CNPJ(groups = CnpjGroup.class)
        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}",
                message = "Invalid document format.")
        String document,
        @NotBlank(message = "Phone number is required")
                @JsonProperty("phone_number")
        String phoneNumber,
        @NotBlank(message = "Password is required")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
                message = "Invalid password format")
        String pwd,
        UserType userType,
        DocumentType documentType) {
}

