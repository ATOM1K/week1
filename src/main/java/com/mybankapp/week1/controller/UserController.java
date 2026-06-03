package com.mybankapp.week1.controller;

import com.mybankapp.week1.dto.UserDto;
import com.mybankapp.week1.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "API для управления пользователями")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(
            summary = "Получить всех пользователей",
            description = "Возвращает список всех пользователей с их адресами"
    )
    @ApiResponse(responseCode = "200", description = "Успешный запрос")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получить пользователя по ID",
            description = "Возвращает информацию о пользователе с указанным ID и его адресами"
    )
    @ApiResponse(responseCode = "200", description = "Пользователь найден")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    public ResponseEntity<UserDto> getUserById(
            @Parameter(description = "ID пользователя", example = "1")
            @PathVariable Long id
    ) {
        UserDto user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @Operation(
            summary = "Создать нового пользователя",
            description = "Создаёт нового пользователя с указанными данными"
    )
    @ApiResponse(responseCode = "201", description = "Пользователь успешно создан")
    @ApiResponse(responseCode = "400", description = "Некорректные данные")
    public ResponseEntity<UserDto> createUser(
            @Parameter(description = "Данные пользователя для создания")
            @RequestBody UserDto userDto
    ) {
        UserDto createdUser = userService.save(userDto);
        return ResponseEntity.status(201).body(createdUser);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновить пользователя",
            description = "Обновляет данные пользователя с указанным ID"
    )
    @ApiResponse(responseCode = "200", description = "Пользователь успешно обновлён")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    @ApiResponse(responseCode = "400", description = "Некорректные данные")
    public ResponseEntity<UserDto> updateUser(
            @Parameter(description = "ID пользователя для обновления", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Обновлённые данные пользователя")
            @RequestBody UserDto userDto
    ) {
        userDto.setId(id);
        UserDto updatedUser = userService.update(userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить пользователя",
            description = "Удаляет пользователя с указанным ID и все его адреса"
    )
    @ApiResponse(responseCode = "204", description = "Пользователь успешно удалён")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID пользователя для удаления", example = "1")
            @PathVariable Long id
    ) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}