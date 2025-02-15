package com.caring.user_service.domain.user.business.adaptor;

import com.caring.user_service.common.annotation.Adaptor;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.manager.entity.ManagerGroup;
import com.caring.user_service.domain.manager.repository.ManagerGroupRepository;
import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Adaptor
@RequiredArgsConstructor
public class UserAdaptorImpl implements UserAdaptor{

    private final UserRepository userRepository;
    private final ManagerGroupRepository managerGroupRepository;

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
        return userRepository.findByUserUuid(userUuid).orElseThrow(() -> new RuntimeException("not found user"));
    }

    @Override
    public List<User> queryUserByManagerGroup(Manager manager) {
        return managerGroupRepository.findUsersByManager(manager);
    }
}
