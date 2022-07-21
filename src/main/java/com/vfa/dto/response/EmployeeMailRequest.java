package com.vfa.dto.response;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EmployeeMailRequest {

    @NotNull(message = "Mail must not be null")
    private String email;
}
