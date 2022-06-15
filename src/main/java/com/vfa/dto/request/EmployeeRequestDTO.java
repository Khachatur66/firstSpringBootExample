package com.vfa.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class EmployeeRequestDTO {

    @Positive(message = "Id must be positive number")
    private int id;

    @NotNull(message = "firstName should not be empty")
    private String firstName;

    @NotNull(message = "firstName should not be empty")
    private String lastName;

    @NotNull(message = "email should not be empty")
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
