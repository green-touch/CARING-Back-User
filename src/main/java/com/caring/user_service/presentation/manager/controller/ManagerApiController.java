package com.caring.user_service.presentation.manager.controller;

import com.caring.user_service.common.annotation.ManagerCode;
import com.caring.user_service.common.annotation.ManagerRoles;
import com.caring.user_service.common.util.RoleUtil;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.presentation.manager.service.*;
import com.caring.user_service.presentation.manager.vo.response.ResponseManager;
import com.caring.user_service.presentation.manager.vo.response.ResponseSpecificManager;
import com.caring.user_service.presentation.manager.vo.response.ResponseSubmission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "[매니저(AUTH)]")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/managers")
public class ManagerApiController {

    private final GetPendingSubmissionsUseCase getPendingSubmissionUseCase;
    private final PermissionRegisteringManagerUseCase permissionRegisteringManagerUseCase;
    private final GetShelterStaffUseCase getShelterStaffUseCase;
    private final GetAllManagersUseCase getAllManagersUseCase;

    @Operation(summary = "아직 수락되지 않은 매니저 신청을 조회합니다.")
    @GetMapping("/submissions")
    public List<ResponseSubmission> getPendingSubmissions(@ManagerRoles List<String> roles) {
        RoleUtil.containManagerRole(ManagerRole.SUPER, roles);
        return getPendingSubmissionUseCase.execute();
    }

    @Operation(summary = "매니저 신청을 수락합니다. 이때 수락하는 주체는 SUPER권한을 가지고 있어야합니다.")
    @PostMapping("/submissions/{submissionUuid}/permission")
    public Long permissionRegisteringManager(@PathVariable String submissionUuid,
                                             @ManagerCode String memberCode,
                                             @ManagerRoles List<String> roles) {
        RoleUtil.containManagerRole(ManagerRole.SUPER, roles);
        return permissionRegisteringManagerUseCase.execute(submissionUuid, memberCode);
    }

    @Operation(summary = "보호소 소속 직원(매니저)들을 조회합니다.")
    @GetMapping("/shelters/{shelterUuid}")
    public List<ResponseSpecificManager> getShelterStaff(@PathVariable String shelterUuid) {
        return getShelterStaffUseCase.execute(shelterUuid);
    }

    @Operation(summary = "모든 직원(매니저)들을 조회합니다.")
    @GetMapping("/all")
    public List<ResponseSpecificManager> getAllManagers() {
        return getAllManagersUseCase.execute();
    }

    //TODO reject submission
}
