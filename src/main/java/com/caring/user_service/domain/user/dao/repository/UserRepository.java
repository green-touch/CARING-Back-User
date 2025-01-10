package com.caring.user_service.domain.user.dao.repository;

import com.caring.user_service.domain.user.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
