package com.vfa.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class EmployeeResponse {

    @Email
    @NotNull(message = "The email field must not be empty")
    private String email;


    @NotNull(message = "The VerificationCode field must not be empty")
    private String verificationCode;
}
