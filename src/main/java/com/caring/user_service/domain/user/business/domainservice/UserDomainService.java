package com.caring.user_service.domain.user.business.domainservice;

import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.manager.entity.ManagerGroup;
import com.caring.user_service.domain.shelter.entity.Shelter;
import com.caring.user_service.domain.user.entity.User;

public interface UserDomainService {

    User registerUser(String name, String password);

    ManagerGroup addUserInManagerGroup(User user, Manager manager);
}
