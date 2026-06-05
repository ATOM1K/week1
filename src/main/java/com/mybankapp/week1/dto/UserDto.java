package com.mybankapp.week1.dto;

import lombok.*;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
    private String email;
    private String passportSeries;
    private String passportNumber;
    private String inn;
    private String maritalStatus;
    private List<AddressDto> addresses;
}