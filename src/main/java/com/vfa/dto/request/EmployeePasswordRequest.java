package com.vfa.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
/*@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode*/
public class EmployeePasswordRequest {

    @Positive(message = "Id must be positive number")
    private int id;

    @NotNull(message = "password should not be empty")
    private String password;
}
