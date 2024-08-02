package com.zakafikry.school.dto;

import lombok.Data;

@Data
public class UserRegistrationDto {
    private String username;
    private String password;
    private String role;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String birthDate;
}
