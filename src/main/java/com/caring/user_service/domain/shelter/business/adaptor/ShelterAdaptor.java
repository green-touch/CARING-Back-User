package com.caring.user_service.domain.shelter.business.adaptor;

import com.caring.user_service.domain.shelter.entity.Shelter;

public interface ShelterAdaptor {

    Shelter queryByShelterUuid(String shelterUuid);
}
