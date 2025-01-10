package com.caring.user_service.domain.user.business.domainservice;

import com.caring.user_service.common.annotation.DomainService;
import com.caring.user_service.common.util.RandomNumberUtil;
import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@DomainService
@Transactional
@RequiredArgsConstructor
public class UserDomainServiceImpl implements UserDomainService{

    private final UserRepository userRepository;

    @Override
    public Long registerUser(String password, String name) {
        User newUser = User.builder()
                .userNumber(RandomNumberUtil.generateRandomUserNumber())
                .userUuid(UUID.randomUUID().toString())
                .password(password)
                .name(name)
                .build();
        return userRepository.save(newUser).getId();
    }
}
