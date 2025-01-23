package com.caring.user_service.domain.shelter.repository;

import com.caring.user_service.domain.shelter.entity.ShelterStaff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShelterStaffRepository extends JpaRepository<ShelterStaff, Long> {
    List<ShelterStaff> findByShelterShelterUuid(String shelterUuid);
}
