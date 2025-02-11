package com.caring.user_service.presentation.user.usecase;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.manager.business.adaptor.ManagerAdaptor;
import com.caring.user_service.domain.manager.business.validate.ManagerValidator;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.user.business.domainservice.UserDomainService;
import com.caring.user_service.presentation.user.vo.RequestUser;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RegisterUserByManagerUseCase {

    private final ManagerAdaptor managerAdaptor;
    private final ManagerValidator managerValidator;
    private final UserDomainService userDomainService;

    public Long execute(RequestUser requestUser, String memberCode) {
        Manager manager = managerAdaptor.queryByMemberCode(memberCode);
        if (!managerValidator.isSuper(manager)) {
            throw new RuntimeException("is not super manager");
        }
        return userDomainService.registerUser(requestUser.getPassword(), requestUser.getName()).getId();
    }
}
