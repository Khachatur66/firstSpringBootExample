package com.vfa.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EmployeePasswordChangingRequest {

    @NotNull(message = "The email field must not be null")
    private String email;

    @NotNull(message = "The passcode field must not be null")
    private String passcode;

    @NotNull(message = "The password field must not be null")
    private String password;
}
