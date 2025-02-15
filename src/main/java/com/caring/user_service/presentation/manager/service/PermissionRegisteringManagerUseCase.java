package com.caring.user_service.presentation.manager.service;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.authority.business.adaptor.AuthorityAdaptor;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.domain.manager.business.adaptor.ManagerAdaptor;
import com.caring.user_service.domain.manager.business.service.ManagerDomainService;
import com.caring.user_service.domain.manager.business.validate.ManagerValidator;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.manager.entity.Submission;
import com.caring.user_service.domain.shelter.business.service.ShelterDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class PermissionRegisteringManagerUseCase {

    private final ManagerAdaptor managerAdaptor;
    private final ManagerValidator managerValidator;
    private final ManagerDomainService managerDomainService;
    private final ShelterDomainService shelterDomainService;
    private final AuthorityAdaptor authorityAdaptor;

    public Long execute(String submissionUuid, String memberCode) {
        // check authorization
        Manager manager = managerAdaptor.queryByMemberCode(memberCode);
        if (!managerValidator.isSuper(manager)) {
            throw new RuntimeException("not authorization");
        }
        // register manager
        Submission submission = managerAdaptor.querySubmissionByUuid(submissionUuid);
        Manager insertManager = managerDomainService.registerManager(
                submission.getName(),
                submission.getPassword(),
                authorityAdaptor.queryByManagerRole(ManagerRole.MANAGE)
        );
        shelterDomainService.addShelterStaff(submission.getShelterUuid(), insertManager);
        // remove submission
        managerDomainService.removeSubmission(submissionUuid);
        return insertManager.getId();
    }
}
