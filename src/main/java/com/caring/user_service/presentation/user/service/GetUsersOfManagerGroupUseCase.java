package com.caring.user_service.presentation.user.service;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.manager.business.adaptor.ManagerAdaptor;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.user.business.adaptor.UserAdaptor;
import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.presentation.user.mapper.UserMapper;
import com.caring.user_service.presentation.user.vo.RequestUser;
import com.caring.user_service.presentation.user.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetUsersOfManagerGroupUseCase {

    private final UserAdaptor userAdaptor;
    private final ManagerAdaptor managerAdaptor;

    public List<ResponseUser> execute(String memberCode) {
        Manager manager = managerAdaptor.queryByMemberCode(memberCode);
        List<User> users = userAdaptor.queryUserByManagerGroup(manager);

        return users.stream()
                .map(user -> UserMapper.INSTANCE.toResponseUserVo(user))
                .toList();
    }
}
