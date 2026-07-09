package com.mybankapp.week1.service;

import com.mybankapp.week1.dto.UserDto;
import com.mybankapp.week1.dto.PaginatedResponse;
import com.mybankapp.week1.dto.UserResponse;
import com.mybankapp.week1.dto.UserSearchRequest;
import com.mybankapp.week1.entity.User;
import com.mybankapp.week1.mapper.UserMapper;
import com.mybankapp.week1.repository.UserRepository;
import com.mybankapp.week1.repository.spec.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public PaginatedResponse<UserResponse> searchUsers(UserSearchRequest request) {
        Sort.Direction direction = "desc".equalsIgnoreCase(request.getSortDir())
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Sort sort = Sort.by(direction, request.getSortBy());
        PageRequest pageable = PageRequest.of(request.getPage(), request.getSize(), sort);

        UserSpecification specification = new UserSpecification(request);
        Page<User> page = userRepository.findAll(specification, pageable);

        List<UserResponse> content = page.getContent().stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());

        return PaginatedResponse.<UserResponse>builder()
                .content(content)
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .number(page.getNumber())
                .size(page.getSize())
                .first(page.isFirst())
                .last(page.isLast())
                .build();
    }
}

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