package com.vfa.dto.response;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EmployeeMailResponse {

    @NotNull(message = "Mail must not be null")
    private String email;
}
