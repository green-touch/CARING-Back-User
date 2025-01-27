package com.caring.user_service.presentation.manager.controller;

import com.caring.user_service.common.annotation.MemberCode;
import com.caring.user_service.presentation.manager.usecase.ApplyManagerUseCase;
import com.caring.user_service.presentation.manager.usecase.GetPendingSubmissionsUseCase;
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

    @PostMapping("/submission/shelters/{shelterUuid}")
    public Long ApplyManager(@PathVariable String shelterUuid,
                             @RequestBody RequestManager requestManager) {
        return applyManagerUseCase.execute(requestManager, shelterUuid);
    }

    @GetMapping("/submissions")
    public List<ResponseSubmission> getPendingSubmissions() {
        return getPendingSubmissionUseCase.execute();
    }

    @GetMapping("/permission/submissions/{uuid}")
    public Long permissionRegisteringManager(@PathVariable String uuid,
                                             @MemberCode String memberCode) {
        return permissionRegisteringManagerUseCase.execute(uuid, memberCode);
    }
}
