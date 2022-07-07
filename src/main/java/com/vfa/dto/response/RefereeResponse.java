package com.vfa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RefereeResponse {
    private String firstName;

    private String lastName;

    private int age;

    private int experience;

    private String city;

    private String country;

    private String street;

    private String building;

    public RefereeResponse(String firstName, int age, String street, String building) {
        this.firstName = firstName;
        this.age = age;
        this.street = street;
        this.building = building;
    }

    public RefereeResponse(String firstName, String lastName, String city, String country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.country = country;
    }
}
