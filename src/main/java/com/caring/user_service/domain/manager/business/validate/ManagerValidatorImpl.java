package com.caring.user_service.domain.manager.business.validate;

import com.caring.user_service.common.annotation.Validator;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.manager.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;

@Validator
@RequiredArgsConstructor
public class ManagerValidatorImpl implements ManagerValidator {

    private final ManagerRepository managerRepository;

    @Override
    public boolean isSuper(Manager manager) {
        return manager.getAuthorities().contains(ManagerRole.SUPER) ? true : false;
    }
}
