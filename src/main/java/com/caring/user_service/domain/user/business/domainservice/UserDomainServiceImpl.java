package com.caring.user_service.domain.user.business.domainservice;

import com.caring.user_service.common.annotation.DomainService;
import com.caring.user_service.common.util.RandomNumberUtil;
import com.caring.user_service.domain.user.entity.Role;
import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@DomainService
@RequiredArgsConstructor
public class UserDomainServiceImpl implements UserDomainService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Long registerUser(String password, String name) {
        User newUser = User.builder()
                .memberCode(RandomNumberUtil.generateRandomMemberCode())
                .userUuid(UUID.randomUUID().toString())
                .role(Role.USER)
                .password(passwordEncoder.encode(password))
                .name(name)
                .build();
        return userRepository.save(newUser).getId();
    }
}
