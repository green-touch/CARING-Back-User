package com.caring.user_service.domain.manager.business.validate;

import com.caring.user_service.common.annotation.Validator;
import com.caring.user_service.domain.authority.entity.Authority;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.manager.repository.ManagerAuthorityRepository;
import com.caring.user_service.domain.manager.repository.ManagerRepository;
import com.caring.user_service.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Validator
@RequiredArgsConstructor
public class ManagerValidatorImpl implements ManagerValidator {

    private final ManagerAuthorityRepository managerAuthorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean isSuper(Manager manager) {
        return manager.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(ManagerRole.SUPER.getKey()::equals);
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
