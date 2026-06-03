package com.mybankapp.week1.service;

import com.mybankapp.week1.dto.UserDto;

import java.util.List;

public interface UserService {

    /**
     * Получить всех пользователей
     */
    List<UserDto> findAll();

    /**
     * Найти пользователя по ID
     */
    UserDto findById(Long id);

    /**
     * Создать нового пользователя
     */
    UserDto save(UserDto userDto);

    /**
     * Обновить существующего пользователя
     */
    UserDto update(UserDto userDto);

    /**
     * Удалить пользователя по ID
     */
    void deleteById(Long id);
}