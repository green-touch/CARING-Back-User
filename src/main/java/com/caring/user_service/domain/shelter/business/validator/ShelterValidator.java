package com.caring.user_service.domain.shelter.business.validator;

import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.user.entity.User;

public interface ShelterValidator {

    boolean isSameShelterUserAndManager(User user, Manager manager);

    boolean isExistShelter(String shelterUuid);
}
