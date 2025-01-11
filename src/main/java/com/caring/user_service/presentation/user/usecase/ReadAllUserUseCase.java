package com.caring.user_service.presentation.user.usecase;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.user.business.adaptor.UserAdaptor;
import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.presentation.user.mapper.UserMapper;
import com.caring.user_service.presentation.user.vo.ResponseUser;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@UseCase
@RequiredArgsConstructor
public class ReadAllUserUseCase {
    private final UserAdaptor userAdaptor;

    public List<ResponseUser> execute() {
        List<User> users = userAdaptor.findAll();
        return users.stream()
                .map(user -> UserMapper.INSTANCE.toResponseUserVo(user))
                .collect(Collectors.toList());
    }
}
