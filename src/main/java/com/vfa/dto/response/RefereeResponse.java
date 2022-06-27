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
}
