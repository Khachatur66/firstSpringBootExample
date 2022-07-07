package com.vfa.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CoachRequest {

    @NotNull(message = "firstName should not be empty")
    private String firstName;

    @NotNull(message = "firstName should not be empty")
    private String lastName;

    @Positive(message = "experience must be positive number")
    private int coachExperience;

    public String getFirstName() {
        return firstName;
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

    public int getCoachExperience() {
        return coachExperience;
    }

    public void setCoachExperience(int coachExperience) {
        this.coachExperience = coachExperience;
    }
}
