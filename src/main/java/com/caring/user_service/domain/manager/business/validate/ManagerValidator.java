package com.caring.user_service.domain.manager.business.validate;

import com.caring.user_service.domain.manager.entity.Manager;

public interface ManagerValidator {

    boolean isSuper(Manager manager);

    void checkPasswordEncode(Manager manager, String password);
}
