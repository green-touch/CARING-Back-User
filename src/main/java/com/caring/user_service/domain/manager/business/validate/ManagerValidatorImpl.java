package com.caring.user_service.domain.manager.business.validate;

import com.caring.user_service.common.annotation.Validator;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.manager.repository.ManagerRepository;
import com.caring.user_service.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Validator
@RequiredArgsConstructor
public class ManagerValidatorImpl implements ManagerValidator {

    private final ManagerRepository managerRepository;

    @Override
    public boolean isSuper(Manager manager) {
        return manager.getAuthorities().contains(ManagerRole.SUPER) ? true : false;
    }

    private final PasswordEncoder passwordEncoder;

    /**
     * only use in filter, so need to throw filterException
     * @param manager
     * @param password
     */
    @Override
    public void checkPasswordEncode(Manager manager, String password) {
        if(!passwordEncoder.matches(password, manager.getPassword()))
            throw new IllegalArgumentException("not match password");
    }
}
