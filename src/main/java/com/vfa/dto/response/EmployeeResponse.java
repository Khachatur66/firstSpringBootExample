package com.vfa.dto.response;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class EmployeeResponse {

    @Positive(message = "The Id field must be positive number")
    private int id;

    @Email
    @NotNull(message = "The email field must not be empty")
    private String email;

    @NotNull(message = "The VerificationCode field must not be empty")
    private String verificationCode;
}
