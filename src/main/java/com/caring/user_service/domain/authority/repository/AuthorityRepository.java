package com.caring.user_service.domain.authority.repository;

import com.caring.user_service.domain.authority.entity.Authority;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByManagerRole(ManagerRole managerRole);
    @Query("SELECT a.managerRole FROM Authority a")
    Set<ManagerRole> findAllManagerRoles();
}
