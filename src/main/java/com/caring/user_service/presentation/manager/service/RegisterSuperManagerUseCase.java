package com.caring.user_service.presentation.manager.service;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.authority.business.adaptor.AuthorityAdaptor;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.domain.manager.business.service.ManagerDomainService;
import com.caring.user_service.presentation.manager.vo.request.RequestManager;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RegisterSuperManagerUseCase {

    private final ManagerDomainService managerDomainService;
    private final AuthorityAdaptor authorityAdaptor;

    public Long execute(RequestManager requestManager) {
        return managerDomainService.registerManager(
                requestManager.getName(),
                requestManager.getPassword(),
                authorityAdaptor.queryByManagerRole(ManagerRole.SUPER)
                ).getId();
    }
}
