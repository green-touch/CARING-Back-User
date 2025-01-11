package com.caring.user_service.domain.user.dao.repository;

import com.caring.user_service.domain.user.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserNumber(String userNumber);
}
