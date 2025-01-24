package com.caring.user_service.domain.shelter.business.adaptor;

import com.caring.user_service.common.annotation.Adaptor;
import com.caring.user_service.domain.shelter.entity.Shelter;
import com.caring.user_service.domain.shelter.repository.ShelterRepository;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class ShelterAdaptorImpl implements ShelterAdaptor{

    private final ShelterRepository shelterRepository;

    @Override
    public Shelter queryByShelterUuid(String shelterUuid) {
        return shelterRepository.findByShelterUuid(shelterUuid)
                .orElseThrow(() -> new RuntimeException("not found shelter"));
    }
}
