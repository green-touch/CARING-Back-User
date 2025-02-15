package com.caring.user_service.domain.shelter.business.validator;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.common.annotation.Validator;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.shelter.repository.ShelterRepository;
import com.caring.user_service.domain.user.entity.User;
import lombok.RequiredArgsConstructor;

@Validator
@RequiredArgsConstructor
public class ShelterValidatorImpl implements ShelterValidator{

    private final ShelterRepository shelterRepository;

    @Override
    public boolean isSameShelterUserAndManager(User user, Manager manager) {
        return user.getShelterUuid().equals(manager.getShelterUuid());
    }

    @Override
    public boolean isExistShelter(String shelterUuid) {
        return shelterRepository.existsByShelterUuid(shelterUuid);
    }
}
