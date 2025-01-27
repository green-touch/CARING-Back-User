package com.caring.user_service.domain.manager.business.validate;

import com.caring.user_service.common.annotation.Validator;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.manager.repository.ManagerAuthorityRepository;
import com.caring.user_service.domain.manager.repository.ManagerRepository;
import com.caring.user_service.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Validator
@RequiredArgsConstructor
public class ManagerValidatorImpl implements ManagerValidator {

    private final ManagerAuthorityRepository managerAuthorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean isSuper(Manager manager) {
//        List<ManagerRole> managerList = managerAuthorityRepository.findByManager(manager)
//                .stream().map(managerAuthority -> managerAuthority.getAuthority().getManagerRole())
//                .toList();

        return manager.getAuthorities().contains(ManagerRole.SUPER) ? true : false;
    }

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
