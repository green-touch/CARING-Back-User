package com.caring.user_service.presentation.manager.controller;

import com.caring.user_service.common.annotation.ManagerCode;
import com.caring.user_service.common.annotation.ManagerRoles;
import com.caring.user_service.common.util.RoleUtil;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.presentation.manager.service.ApplyManagerUseCase;
import com.caring.user_service.presentation.manager.service.GetPendingSubmissionsUseCase;
import com.caring.user_service.presentation.manager.service.PermissionRegisteringManagerUseCase;
import com.caring.user_service.presentation.manager.service.RegisterSuperManagerUseCase;
import com.caring.user_service.presentation.manager.vo.RequestManager;
import com.caring.user_service.presentation.manager.vo.ResponseSubmission;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/managers")
public class ManagerApiController {

    private final GetPendingSubmissionsUseCase getPendingSubmissionUseCase;
    private final PermissionRegisteringManagerUseCase permissionRegisteringManagerUseCase;

    @GetMapping("/submissions")
    public List<ResponseSubmission> getPendingSubmissions(@ManagerRoles List<String> roles) {
        RoleUtil.containManagerRole(ManagerRole.SUPER, roles);
        return getPendingSubmissionUseCase.execute();
    }

    @PostMapping("/submissions/{submissionUuid}/permission")
    public Long permissionRegisteringManager(@PathVariable String submissionUuid,
                                             @ManagerCode String memberCode,
                                             @ManagerRoles List<String> roles) {
        RoleUtil.containManagerRole(ManagerRole.SUPER, roles);
        return permissionRegisteringManagerUseCase.execute(submissionUuid, memberCode);
    }

    //TODO reject submission
}
