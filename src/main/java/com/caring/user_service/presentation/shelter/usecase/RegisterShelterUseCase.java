package com.caring.user_service.presentation.shelter.usecase;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.manager.business.validate.ManagerValidator;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.shelter.business.service.ShelterDomainService;
import com.caring.user_service.presentation.manager.vo.RequestManager;
import com.caring.user_service.presentation.shelter.vo.RequestShelter;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RegisterShelterUseCase {

    private final ManagerValidator managerValidator;
    private final ShelterDomainService shelterDomainService;

    public Long execute(RequestShelter requestShelter, Manager manager) {
        managerValidator.isSuper(manager);
        return shelterDomainService.registerShelter(requestShelter.getName(), requestShelter.getLocation());
    }
}
