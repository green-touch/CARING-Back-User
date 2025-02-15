package com.caring.user_service.presentation.user.usecase;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.manager.business.adaptor.ManagerAdaptor;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.user.business.adaptor.UserAdaptor;
import com.caring.user_service.domain.user.business.domainservice.UserDomainService;
import com.caring.user_service.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class AddUserInManagerGroupUseCase {

    private final UserAdaptor userAdaptor;
    private final ManagerAdaptor managerAdaptor;
    private final UserDomainService userDomainService;

    public Long execute(String userUuid, String managerUuid) {
        User user = userAdaptor.queryUserByUserUuid(userUuid);
        Manager manager = managerAdaptor.queryByManagerUuid(managerUuid);
        //TODO shelter 소속이 같은지 확인 필요
        return userDomainService.addUserInManagerGroup(user, manager).getId();
    }
}
