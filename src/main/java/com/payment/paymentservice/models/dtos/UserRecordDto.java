package com.payment.paymentservice.models.dtos;

import com.payment.paymentservice.enums.DocumentType;
import com.payment.paymentservice.enums.UserType;
import com.payment.paymentservice.services.interfaces.groups.CnpjGroup;
import com.payment.paymentservice.services.interfaces.groups.CpfGroup;
import com.payment.paymentservice.services.validators.UserRecordDtoGroupSequenceProvider;
import jakarta.validation.constraints.Email;
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
        @NotBlank(message = "Document is required")
        @CPF(groups = CpfGroup.class)
        @CNPJ(groups = CnpjGroup.class)
        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}",
                message = "Invalid document format.")
        String document,
        @NotBlank(message = "Address is required")
        String address,
        @NotBlank(message = "Email is required")
                @Email
        String email,
        @NotBlank(message = "Phone number is required")
        String phone_number,
        @NotBlank(message = "Password is required")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
                message = "Invalid password format")
        String pwd,
        UserType userType,
        DocumentType documentType) {
}

