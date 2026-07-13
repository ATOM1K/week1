package com.mybankapp.week1.mapper;

import com.mybankapp.week1.dto.UserDto;
import com.mybankapp.week1.entity.User;
import com.mybankapp.week1.dto.UserResponse;
//import org.mapstruct.*;
import java.util.List;
import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface UserMapper {

    @Mapping(source = "addresses", target = "addresses")
    UserDto toDto(User user);

    @Mapping(source = "addresses", target = "addresses")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(UserDto userDto, @MappingTarget User user);

    List<UserDto> toDtoList(List<User> users);

    @InheritInverseConfiguration
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    User toEntity(UserDto userDto);

    //добавил
    @Mapping(source = "address.street", target = "street")
    @Mapping(source = "address.city", target = "city")
    @Mapping(source = "address.zipCode", target = "zipCode")
    @Mapping(source = "address.country", target = "country")
    UserResponse toResponse(@NonNull User user);
}