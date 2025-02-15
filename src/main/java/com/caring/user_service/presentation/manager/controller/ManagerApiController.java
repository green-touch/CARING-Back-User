package com.caring.user_service.presentation.manager.controller;

import com.caring.user_service.common.annotation.ManagerCode;
import com.caring.user_service.common.annotation.MemberCode;
import com.caring.user_service.common.annotation.Roles;
import com.caring.user_service.common.util.RoleUtil;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.presentation.manager.usecase.ApplyManagerUseCase;
import com.caring.user_service.presentation.manager.usecase.GetPendingSubmissionsUseCase;
import com.caring.user_service.presentation.manager.usecase.PermissionRegisteringManagerUseCase;
import com.caring.user_service.presentation.manager.usecase.RegisterSuperManagerUseCase;
import com.caring.user_service.presentation.manager.vo.RequestManager;
import com.caring.user_service.presentation.manager.vo.ResponseSubmission;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/managers")
public class ManagerApiController {

    private final RegisterSuperManagerUseCase registerSuperManagerUseCase;
    private final ApplyManagerUseCase applyManagerUseCase;
    private final GetPendingSubmissionsUseCase getPendingSubmissionUseCase;
    private final PermissionRegisteringManagerUseCase permissionRegisteringManagerUseCase;

    @PostMapping("/super")
    public Long registerSuperManager(@RequestBody RequestManager requestManager) {
        return registerSuperManagerUseCase.execute(requestManager);
    }

    @PostMapping("/submissions/shelters/{uuid}")
    public Long applyManager(@PathVariable String uuid,
                             @RequestBody RequestManager requestManager) {
        return applyManagerUseCase.execute(requestManager, uuid);
    }

    @GetMapping("/submissions")
    public List<ResponseSubmission> getPendingSubmissions(@Roles List<String> roles) {
        RoleUtil.containManagerRole(ManagerRole.SUPER, roles);
        return getPendingSubmissionUseCase.execute();
    }

    @PostMapping("/submissions/{uuid}/permission")
    public Long permissionRegisteringManager(@PathVariable String uuid,
                                             @ManagerCode String memberCode,
                                             @Roles List<String> roles) {
        RoleUtil.containManagerRole(ManagerRole.SUPER, roles);
        return permissionRegisteringManagerUseCase.execute(uuid, memberCode);
    }

    //TODO reject submission
}
