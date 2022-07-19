package com.vfa.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ReAuthenticationRequest {

    @NotNull
    private String refreshToken;
}
