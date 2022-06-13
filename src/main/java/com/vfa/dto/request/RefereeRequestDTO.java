package com.vfa.dto.request;

public class RefereeRequestDTO {

    private String firstName;

    private String lastName;

    private int refereeExperience;

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

    public int getRefereeExperience() {
        return refereeExperience;
    }

    public void setRefereeExperience(int refereeExperience) {
        this.refereeExperience = refereeExperience;
    }
}
