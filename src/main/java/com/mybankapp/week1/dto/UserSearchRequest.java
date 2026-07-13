package com.mybankapp.week1.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchRequest {
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    // поля из address
    private String street;
    private String city;
    private String zipCode;
    private String country;

    private String sortBy;        // "firstName", "city" и т.п.
    private String sortDir;       // "asc" или "desc"
    private int page = 0;
    private int size = 20;
}