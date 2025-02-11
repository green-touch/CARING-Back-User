package com.caring.user_service.domain.user.business.domainservice;

import com.caring.user_service.domain.shelter.entity.Shelter;
import com.caring.user_service.domain.shelter.entity.ShelterGroup;
import com.caring.user_service.domain.user.entity.User;

public interface UserDomainService {

    User registerUser(String password, String name);

    ShelterGroup addUserInShelterGroup(Shelter shelter, User user);
}
