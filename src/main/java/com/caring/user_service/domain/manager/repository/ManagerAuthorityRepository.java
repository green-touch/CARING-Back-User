package com.caring.user_service.domain.manager.repository;

import com.caring.user_service.domain.authority.entity.ManagerAuthority;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.domain.manager.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ManagerAuthorityRepository extends JpaRepository<ManagerAuthority, Long> {
    List<ManagerAuthority> findByManager(Manager manager);
}
