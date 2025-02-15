package com.caring.user_service.domain.user.business.domainservice;

import com.caring.user_service.common.annotation.DomainService;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.manager.entity.ManagerGroup;
import com.caring.user_service.domain.manager.repository.ManagerGroupRepository;
import com.caring.user_service.domain.shelter.entity.Shelter;
import com.caring.user_service.domain.user.entity.Role;
import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static com.caring.user_service.common.consts.StaticVariable.USER_MEMBER_CODE_PRESET;
import static com.caring.user_service.common.util.RandomNumberUtil.generateRandomMemberCode;

@DomainService
@RequiredArgsConstructor
public class UserDomainServiceImpl implements UserDomainService{

    private final UserRepository userRepository;
    private final ManagerGroupRepository managerGroupRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(String password, String name) {
        User newUser = User.builder()
                .memberCode(generateRandomMemberCode(USER_MEMBER_CODE_PRESET))
                .userUuid(UUID.randomUUID().toString())
                .role(Role.USER)
                .password(passwordEncoder.encode(password))
                .name(name)
                .build();
        return userRepository.save(newUser);
    }

    @Override
    public ManagerGroup addUserInManagerGroup(User user, Manager manager) {
        ManagerGroup newManagerGroup = ManagerGroup.builder()
                .manager(manager)
                .user(user)
                .build();
        return managerGroupRepository.save(newManagerGroup);
    }

}
