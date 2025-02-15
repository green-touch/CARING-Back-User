package com.caring.user_service.presentation.manager.service;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.manager.business.service.ManagerDomainService;
import com.caring.user_service.presentation.manager.vo.RequestManager;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class ApplyManagerUseCase {

    private final ManagerDomainService managerDomainService;

    public Long execute(RequestManager requestManager, String shelterUuid) {
        return managerDomainService.applyManager(
                requestManager.getName(),
                requestManager.getPassword(),
                shelterUuid).getId();
    }
}
