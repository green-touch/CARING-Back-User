package com.caring.user_service.presentation.manager.controller;

import com.caring.user_service.presentation.manager.service.ApplyManagerUseCase;
import com.caring.user_service.presentation.manager.service.RegisterSuperManagerUseCase;
import com.caring.user_service.presentation.manager.vo.request.RequestManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@Tag(name = "[매니저(액세스 허용)]")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/access/managers")
public class ManagerAccessApiController {

    private final RegisterSuperManagerUseCase registerSuperManagerUseCase;
    private final ApplyManagerUseCase applyManagerUseCase;

    @Operation(summary = "SUPER 권한을 가진 매니저를 서버에 등록합니다.")
    @PostMapping("/super")
    public Long registerSuperManager(@RequestBody RequestManager requestManager) {
        return registerSuperManagerUseCase.execute(requestManager);
    }

    @Operation(summary = "특정 보호소의 매니저를 신청합니다.")
    @PostMapping("/submissions/shelters/{shelterUuid}")
    public Long applyManager(@PathVariable String shelterUuid,
                             @RequestBody RequestManager requestManager) {
        return applyManagerUseCase.execute(requestManager, shelterUuid);
    }
}
