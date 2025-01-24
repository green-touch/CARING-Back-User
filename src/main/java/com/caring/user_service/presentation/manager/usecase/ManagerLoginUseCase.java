package com.caring.user_service.presentation.manager.usecase;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.manager.business.adaptor.ManagerAdaptor;
import com.caring.user_service.domain.manager.business.validate.ManagerValidator;
import com.caring.user_service.domain.manager.entity.Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ManagerLoginUseCase {

    private final ManagerAdaptor managerAdaptor;
    private final ManagerValidator managerValidator;

    public Manager execute(String memberCode, String password) {
        Manager manager = managerAdaptor.queryByMemberCode(memberCode);
        managerValidator.checkPasswordEncode(manager, password);
        return manager;
    }
}
