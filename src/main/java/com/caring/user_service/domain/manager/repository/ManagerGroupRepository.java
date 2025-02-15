package com.caring.user_service.domain.manager.repository;

import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.manager.entity.ManagerGroup;
import com.caring.user_service.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ManagerGroupRepository extends JpaRepository<ManagerGroup, Long> {
    @Query("SELECT mg.user FROM ManagerGroup mg WHERE mg.manager = :manager")
    List<User> findUsersByManager(@Param("manager") Manager manager);
}
