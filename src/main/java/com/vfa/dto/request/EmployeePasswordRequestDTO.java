package com.vfa.dto.request;

import javax.validation.constraints.NotNull;

public class EmployeePasswordRequestDTO {


    private int id;

    @NotNull(message = "password should not be empty")
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
