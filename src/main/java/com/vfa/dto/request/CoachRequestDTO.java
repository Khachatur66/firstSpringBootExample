package com.vfa.dto.request;

public class CoachRequestDTO {

    private String firstName;

    private String lastName;

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
