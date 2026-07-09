package com.mybankapp.week1.repository.spec;


import com.mybankapp.week1.dto.UserSearchRequest;
import com.mybankapp.week1.entity.User;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserSpecification implements Specification<User> {

    private final UserSearchRequest request;

    public UserSpecification(UserSearchRequest request) {
        this.request = request;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        // Фильтрация по полям User
        if (request.getFirstName() != null && !request.getFirstName().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("firstName")), "%" + request.getFirstName().toLowerCase() + "%"));
        }
        if (request.getLastName() != null && !request.getLastName().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("lastName")), "%" + request.getLastName().toLowerCase() + "%"));
        }
        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("email")), "%" + request.getEmail().toLowerCase() + "%"));
        }
        if (request.getAge() != null) {
            predicates.add(cb.equal(root.get("age"), request.getAge()));
        }

        // Фильтрация по Address (предполагаем связь user.address)
        Join<User, com.example.week1.entity.Address> addressJoin = root.join("address", JoinType.LEFT);

        if (request.getStreet() != null && !request.getStreet().isBlank()) {
            predicates.add(cb.like(cb.lower(addressJoin.get("street")), "%" + request.getStreet().toLowerCase() + "%"));
        }
        if (request.getCity() != null && !request.getCity().isBlank()) {
            predicates.add(cb.like(cb.lower(addressJoin.get("city")), "%" + request.getCity().toLowerCase() + "%"));
        }
        if (request.getZipCode() != null && !request.getZipCode().isBlank()) {
            predicates.add(cb.like(cb.lower(addressJoin.get("zipCode")), "%" + request.getZipCode().toLowerCase() + "%"));
        }
        if (request.getCountry() != null && !request.getCountry().isBlank()) {
            predicates.add(cb.like(cb.lower(addressJoin.get("country")), "%" + request.getCountry().toLowerCase() + "%"));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}