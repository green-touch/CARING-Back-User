package com.caring.user_service.domain.user.repository;

import com.caring.user_service.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
