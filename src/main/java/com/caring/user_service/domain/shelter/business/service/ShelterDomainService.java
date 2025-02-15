package com.caring.user_service.domain.shelter.business.service;

import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.shelter.entity.Shelter;
import com.caring.user_service.domain.user.entity.User;

public interface ShelterDomainService {

    Shelter registerShelter(String name, String location);

    void addShelterStaff(String shelterUuid, Manager manager);

    void addShelterGroup(String shelterUuid, User user);
}
