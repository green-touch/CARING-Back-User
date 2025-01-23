package com.caring.user_service.domain.authority.repository;

import com.caring.user_service.domain.authority.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
