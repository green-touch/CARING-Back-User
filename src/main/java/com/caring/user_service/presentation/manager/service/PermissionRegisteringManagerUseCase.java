package com.caring.user_service.presentation.manager.service;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.authority.business.adaptor.AuthorityAdaptor;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.domain.manager.business.adaptor.ManagerAdaptor;
import com.caring.user_service.domain.manager.business.service.ManagerDomainService;
import com.caring.user_service.domain.manager.business.validate.ManagerValidator;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.manager.entity.Submission;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class PermissionRegisteringManagerUseCase {

    private final ManagerAdaptor managerAdaptor;
    private final ManagerValidator managerValidator;
    private final ManagerDomainService managerDomainService;
    private final AuthorityAdaptor authorityAdaptor;

    public Long execute(String uuid, String memberCode) {
        // check authorization
        Manager manager = managerAdaptor.queryByMemberCode(memberCode);
        if (!managerValidator.isSuper(manager)) {
            throw new RuntimeException("not authorization");
        }
        // register manager
        Submission submission = managerAdaptor.querySubmissionByUuid(uuid);
        Manager insertManager = managerDomainService.registerManager(
                submission.getName(),
                submission.getPassword(),
                authorityAdaptor.queryByManagerRole(ManagerRole.MANAGE)
        );
        // remove submission
        managerDomainService.removeSubmission(uuid);
        return insertManager.getId();
    }
}
