package com.mybankapp.week1.dto;

import lombok.Data;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AddressDto {
    private Long id;
    private String street;
    private String city;
    private String postalCode;
}