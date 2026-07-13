package com.mybankapp.week1.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;

    // адрес
    private String street;
    private String city;
    private String zipCode;
    private String country;
}
