package com.caring.user_service.presentation.manager.controller;

import com.caring.user_service.common.annotation.ManagerCode;
import com.caring.user_service.common.annotation.ManagerRoles;
import com.caring.user_service.common.util.RoleUtil;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.presentation.manager.service.*;
import com.caring.user_service.presentation.manager.vo.response.ResponseManager;
import com.caring.user_service.presentation.manager.vo.response.ResponseSpecificManager;
import com.caring.user_service.presentation.manager.vo.response.ResponseSubmission;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/managers")
public class ManagerApiController {

    private final GetPendingSubmissionsUseCase getPendingSubmissionUseCase;
    private final PermissionRegisteringManagerUseCase permissionRegisteringManagerUseCase;
    private final GetShelterStaffUseCase getShelterStaffUseCase;
    private final GetAllManagersUseCase getAllManagersUseCase;

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

    @GetMapping("/shelter/{shelterUuid}")
    public List<ResponseSpecificManager> getShelterStaff(@PathVariable String shelterUuid) {
        return getShelterStaffUseCase.execute(shelterUuid);
    }

    @GetMapping("/all")
    public List<ResponseSpecificManager> getAllManagers() {
        return getAllManagersUseCase.execute();
    }

    //TODO reject submission
}
