package com.caring.user_service.domain.shelter.business.service;

import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.shelter.entity.Shelter;
import com.caring.user_service.domain.shelter.entity.ShelterStaff;

public interface ShelterDomainService {

    Shelter registerShelter(String name, String location);

    ShelterStaff addShelterStaff(Shelter shelter, Manager manager);
}
