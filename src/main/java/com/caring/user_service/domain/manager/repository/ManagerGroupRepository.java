package com.caring.user_service.domain.manager.repository;

import com.caring.user_service.domain.manager.entity.ManagerGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerGroupRepository extends JpaRepository<ManagerGroup, Long> {
}
