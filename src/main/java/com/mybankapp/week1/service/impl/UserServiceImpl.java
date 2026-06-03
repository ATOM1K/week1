package com.mybankapp.week1.service.impl;

import com.mybankapp.week1.dto.UserDto;
import com.mybankapp.week1.entity.Address;
import com.mybankapp.week1.entity.User;
import com.mybankapp.week1.mapper.UserMapper;
import com.mybankapp.week1.repository.AddressRepository;
import com.mybankapp.week1.repository.UserRepository;
import com.mybankapp.week1.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository,
                           AddressRepository addressRepository,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Пользователь с ID " + id + " не найден"));
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public UserDto save(UserDto userDto) {
        // Проверка на дублирование email
        if (userDto.getEmail() != null &&
                userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Пользователь с таким email уже существует");
        }

        // Проверка на дублирование паспорта
        if (userDto.getPassportSeries() != null && userDto.getPassportNumber() != null &&
                userRepository.existsByPassportSeriesAndPassportNumber(
                        userDto.getPassportSeries(), userDto.getPassportNumber())) {
            throw new IllegalArgumentException("Пользователь с такими данными паспорта уже существует");
        }

        User user = userMapper.toEntity(userDto);
        User savedUser = userRepository.save(user);

        // Сохраняем адреса, если они есть
        if (userDto.getAddresses() != null) {
            List<Address> addresses = userDto.getAddresses().stream()
                    .map(addressDto -> {
                        Address address = userMapper.toAddressEntity(addressDto);
                        address.setUser(savedUser);
                        return address;
                    }).collect(Collectors.toList());
            addressRepository.saveAll(addresses);
        }

        return userMapper.toDto(savedUser);
    }

    @Override
    @Transactional
    public UserDto update(UserDto userDto) {
        Long userId = userDto.getId();
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Пользователь с ID " + userId + " не найден"));

        // Обновляем основные данные пользователя
        userMapper.updateEntityFromDto(userDto, existingUser);

        // Удаляем старые адреса и сохраняем новые
        addressRepository.deleteByUserId(userId);

        if (userDto.getAddresses() != null) {
            List<Address> newAddresses = userDto.getAddresses().stream()
                    .map(addressDto -> {
                        Address address = userMapper.toAddressEntity(addressDto);
                        address.setUser(existingUser);
                        return address;
                    }).collect(Collectors.toList());
            addressRepository.saveAll(newAddresses);
        }

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toDto(updatedUser);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("Пользователь с ID " + id + " не найден");
        }
        // Сначала удаляем все адреса пользователя
        addressRepository.deleteByUserId(id);
        // Затем удаляем самого пользователя
        userRepository.deleteById(id);
    }
}