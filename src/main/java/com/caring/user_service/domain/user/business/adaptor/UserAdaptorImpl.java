package com.caring.user_service.domain.user.business.adaptor;

import com.caring.user_service.common.annotation.Adaptor;
import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Adaptor
@RequiredArgsConstructor
public class UserAdaptorImpl implements UserAdaptor{

    private final UserRepository userRepository;

    @Override
    public User queryUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("not found user"));
    }

    @Override
    public List<User> queryAll() {
        return userRepository.findAll();
    }

    @Override
    public User queryUserByMemberCode(String memberCode) {
        return userRepository.findByMemberCode(memberCode).orElseThrow(() -> new RuntimeException("not found user"));
    }

    @Override
    public User queryUserByUserUuid(String userUuid) {
        return userRepository.findByUserUuid(userUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User not found with UUID: " + userUuid));
    }

    @Override
    public List<User> queryByUserUuidList(List<String> userUuidList) {
        return userRepository.findByUserUuidIn(userUuidList);
    }
}
