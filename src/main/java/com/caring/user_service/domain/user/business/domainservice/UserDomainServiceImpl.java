package com.caring.user_service.domain.user.business.domainservice;

import com.caring.user_service.common.annotation.DomainService;
import com.caring.user_service.domain.user.entity.Role;
import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.util.UUID;

import static com.caring.user_service.common.consts.StaticVariable.USER_MEMBER_CODE_PRESET;
import static com.caring.user_service.common.util.RandomNumberUtil.generateRandomMemberCode;

@DomainService
@RequiredArgsConstructor
public class UserDomainServiceImpl implements UserDomainService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(String name, String password) {
        validateName(name);
        validatePassword(password);

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
    public User registerUserWithShelterUuid(String name, String password, String shelterUuid) {
        validateName(name);
        validatePassword(password);

        User newUser = User.builder()
                .memberCode(generateRandomMemberCode(USER_MEMBER_CODE_PRESET))
                .userUuid(UUID.randomUUID().toString())
                .role(Role.USER)
                .password(passwordEncoder.encode(password))
                .name(name)
                .shelterUuid(shelterUuid)
                .build();
        return userRepository.save(newUser);
    }

    private void validateName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("이름은 null일 수 없습니다");
        }
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("이름은 빈 문자열일 수 없습니다");
        }
    }

    private void validatePassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("비밀번호는 null일 수 없습니다");
        }
        if (!StringUtils.hasText(password)) {
            throw new IllegalArgumentException("비밀번호는 빈 문자열일 수 없습니다");
        }
    }
}
