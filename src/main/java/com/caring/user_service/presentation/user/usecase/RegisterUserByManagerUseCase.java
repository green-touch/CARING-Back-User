package com.caring.user_service.presentation.user.usecase;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.manager.business.adaptor.ManagerAdaptor;
import com.caring.user_service.domain.manager.business.validate.ManagerValidator;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.shelter.business.adaptor.ShelterAdaptor;
import com.caring.user_service.domain.shelter.business.service.ShelterDomainService;
import com.caring.user_service.domain.shelter.entity.Shelter;
import com.caring.user_service.domain.user.business.domainservice.UserDomainService;
import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.presentation.user.vo.RequestUser;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RegisterUserByManagerUseCase {

    private final UserDomainService userDomainService;
    private final ShelterAdaptor shelterAdaptor;
    private final ShelterDomainService shelterDomainService;

    public Long execute(RequestUser requestUser, String shelterUuid) {
        User user = userDomainService.registerUser(requestUser.getPassword(), requestUser.getName());
        Shelter shelter = shelterAdaptor.queryByShelterUuid(shelterUuid);
        shelterDomainService.addShelterGroup(shelterUuid, user);
        return user.getId();
    }
}
