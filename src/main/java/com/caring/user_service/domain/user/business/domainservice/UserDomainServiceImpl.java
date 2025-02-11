package com.caring.user_service.domain.user.business.domainservice;

import com.caring.user_service.common.annotation.DomainService;
import com.caring.user_service.common.consts.StaticVariable;
import com.caring.user_service.common.util.RandomNumberUtil;
import com.caring.user_service.domain.shelter.entity.Shelter;
import com.caring.user_service.domain.shelter.entity.ShelterGroup;
import com.caring.user_service.domain.shelter.repository.ShelterGroupRepository;
import com.caring.user_service.domain.user.entity.Role;
import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.caring.user_service.common.consts.StaticVariable.USER_MEMBER_CODE_PRESET;
import static com.caring.user_service.common.util.RandomNumberUtil.generateRandomMemberCode;

@DomainService
@RequiredArgsConstructor
public class UserDomainServiceImpl implements UserDomainService{

    private final UserRepository userRepository;
    private final ShelterGroupRepository shelterGroupRepository;
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
    public ShelterGroup addUserInShelterGroup(Shelter shelter, User user) {
        ShelterGroup newShelterGroup = ShelterGroup.builder()
                .shelter(shelter)
                .user(user)
                .build();
        return shelterGroupRepository.save(newShelterGroup);
    }
}
