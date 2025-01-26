package com.caring.user_service.domain.authority.business.adaptor;

import com.caring.user_service.domain.authority.entity.Authority;
import com.caring.user_service.domain.authority.entity.ManagerRole;

import java.util.List;

public interface AuthorityAdaptor {

    Authority queryByManagerRole(ManagerRole managerRole);

    List<Authority> queryAll();
}
