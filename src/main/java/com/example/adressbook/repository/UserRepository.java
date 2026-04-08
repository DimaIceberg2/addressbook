package com.example.adressbook.repository;

import com.example.adressbook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAddress(String email);

    //без оптимизации, возможно стоит добавить индексы, или возможно использовать criteria api
    @Query("SELECT u FROM User u WHERE " +
            "LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.secondName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.patronymic) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "u.phoneNumber LIKE CONCAT('%', :search, '%') OR " +
            "LOWER(u.emailAddress) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<User> searchUsers(@Param("search") String search);
}
