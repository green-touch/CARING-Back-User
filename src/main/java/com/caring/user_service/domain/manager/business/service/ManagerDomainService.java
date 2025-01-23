package com.caring.user_service.domain.manager.business.service;

import com.caring.user_service.domain.authority.entity.Authority;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.domain.manager.entity.Manager;

public interface ManagerDomainService {

    Long registerManager(String name, String password, Authority authority);
}
