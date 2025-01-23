package com.caring.user_service.domain.shelter.repository;

import com.caring.user_service.domain.shelter.entity.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {
}
