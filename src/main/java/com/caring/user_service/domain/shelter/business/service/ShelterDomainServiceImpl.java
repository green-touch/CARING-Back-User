package com.caring.user_service.domain.shelter.business.service;

import com.caring.user_service.common.annotation.DomainService;
import com.caring.user_service.domain.shelter.entity.Shelter;
import com.caring.user_service.domain.shelter.repository.ShelterRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@DomainService
@RequiredArgsConstructor
public class ShelterDomainServiceImpl implements ShelterDomainService{

    private final ShelterRepository shelterRepository;

    @Override
    public Long registerShelter(String name, String location) {
        Shelter newShelter = Shelter.builder()
                .name(name)
                .location(location)
                .shelterUuid(UUID.randomUUID().toString())
                .build();
        return shelterRepository.save(newShelter).getId();
    }
}
