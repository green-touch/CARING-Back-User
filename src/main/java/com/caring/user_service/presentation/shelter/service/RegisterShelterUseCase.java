package com.caring.user_service.presentation.shelter.service;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.manager.business.adaptor.ManagerAdaptor;
import com.caring.user_service.domain.manager.business.validate.ManagerValidator;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.shelter.business.service.ShelterDomainService;
import com.caring.user_service.presentation.shelter.vo.RequestShelter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@UseCase
@Transactional
@RequiredArgsConstructor
public class RegisterShelterUseCase {

    private final ManagerAdaptor managerAdaptor;
    private final ManagerValidator managerValidator;
    private final ShelterDomainService shelterDomainService;

    public Long execute(RequestShelter requestShelter, String memberCode) {
        Manager manager = managerAdaptor.queryByMemberCode(memberCode);
        if (!managerValidator.isSuper(manager)) {
            throw new RuntimeException("not authorization");
        }
        return shelterDomainService.registerShelter(requestShelter.getName(), requestShelter.getLocation()).getId();
    }
}
