package com.mybankapp.week1.repository;

import com.mybankapp.week1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Поиск пользователя по email
     */
    Optional<User> findByEmail(String email);

    /**
     * Поиск пользователей по имени (с использованием индекса idx_user_name)
     */
    List<User> findByFirstName(String firstName);

    /**
     * Проверка существования пользователя по email
     */
    boolean existsByEmail(String email);

    /**
     * Проверка существования пользователя по паспорту (серия + номер)
     */
    boolean existsByPassportSeriesAndPassportNumber(String passportSeries, String passportNumber);
}