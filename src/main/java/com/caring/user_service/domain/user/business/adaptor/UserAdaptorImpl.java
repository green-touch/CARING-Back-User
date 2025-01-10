package com.caring.user_service.domain.user.business.adaptor;

import com.caring.user_service.common.annotation.Adaptor;
import com.caring.user_service.domain.user.dao.entity.User;
import com.caring.user_service.domain.user.dao.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Adaptor
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserAdaptorImpl implements UserAdaptor{

    private final UserRepository userRepository;

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("not found user"));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
