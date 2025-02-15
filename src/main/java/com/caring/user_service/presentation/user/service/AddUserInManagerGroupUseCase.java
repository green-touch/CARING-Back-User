package com.caring.user_service.presentation.user.service;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.manager.business.adaptor.ManagerAdaptor;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.shelter.business.validator.ShelterValidator;
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
    private final ShelterValidator shelterValidator;
    private final UserDomainService userDomainService;

    public Long execute(String userUuid, String managerUuid) {
        User user = userAdaptor.queryUserByUserUuid(userUuid);
        Manager manager = managerAdaptor.queryByManagerUuid(managerUuid);
        if (!shelterValidator.isSameShelterUserAndManager(user, manager)) {
            throw new RuntimeException("not equal shelter");
        }
        return userDomainService.addUserInManagerGroup(user, manager).getId();
    }
}
