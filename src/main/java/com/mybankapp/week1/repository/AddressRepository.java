package com.mybankapp.week1.repository;

import com.mybankapp.week1.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    /**
     * Получение всех адресов пользователя по его ID
     */
    List<Address> findByUserId(Long userId);

    /**
     * Удаление всех адресов пользователя
     */
    void deleteByUserId(Long userId);

    /**
     * Проверка существования адресов у пользователя
     */
    boolean existsByUserId(Long userId);
}