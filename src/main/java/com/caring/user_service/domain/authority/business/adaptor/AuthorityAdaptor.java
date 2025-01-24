package com.caring.user_service.domain.authority.business.adaptor;

import com.caring.user_service.domain.authority.entity.Authority;

public interface AuthorityAdaptor {

    Authority queryByManagerRoleKey(String key);
}
