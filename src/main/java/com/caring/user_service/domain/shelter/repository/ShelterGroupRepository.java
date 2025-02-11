package com.caring.user_service.domain.shelter.repository;

import com.caring.user_service.domain.shelter.entity.ShelterGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelterGroupRepository extends JpaRepository<ShelterGroup, Long> {
}
