package com.caring.user_service.domain.shelter.business.service;

import com.caring.user_service.common.annotation.DomainService;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.shelter.entity.Shelter;
import com.caring.user_service.domain.shelter.repository.ShelterRepository;
import com.caring.user_service.domain.user.entity.User;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@DomainService
@RequiredArgsConstructor
public class ShelterDomainServiceImpl implements ShelterDomainService{

    private final ShelterRepository shelterRepository;

    @Override
    public Shelter registerShelter(String name, String location) {
        Shelter newShelter = Shelter.builder()
                .name(name)
                .location(location)
                .shelterUuid(UUID.randomUUID().toString())
                .build();
        return shelterRepository.save(newShelter);
    }

    @Override
    public void addShelterStaff(String shelterUuid, Manager manager) {
        manager.groupedInShelter(shelterUuid);
    }

    @Override
    public void addShelterGroup(String shelterUuid, User user) {
        user.groupedInShelter(shelterUuid);
    }

}
