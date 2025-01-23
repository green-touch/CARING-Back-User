package com.caring.user_service.domain.manager.repository;

import com.caring.user_service.domain.manager.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Optional<Manager> findByMemberCode(String memberCode);

    Optional<Manager> findByManagerUuid(String managerUuid);
}
