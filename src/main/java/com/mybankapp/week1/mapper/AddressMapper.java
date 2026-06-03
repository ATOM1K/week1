package com.mybankapp.week1.mapper;

import com.mybankapp.week1.dto.AddressDto;
import com.mybankapp.week1.entity.Address;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressDto toDto(Address address);

    @InheritInverseConfiguration
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Address toEntity(AddressDto addressDto);

    List<AddressDto> toDtoList(List<Address> addresses);

    List<Address> toEntityList(List<AddressDto> addressDtos);
}