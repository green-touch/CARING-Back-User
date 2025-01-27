package com.caring.user_service.presentation.manager.controller;

import com.caring.user_service.presentation.manager.usecase.ApplyManagerUseCase;
import com.caring.user_service.presentation.manager.usecase.RegisterSuperManagerUseCase;
import com.caring.user_service.presentation.manager.vo.RequestManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/managers")
public class ManagerApiController {

    private final RegisterSuperManagerUseCase registerSuperManagerUseCase;
    private final ApplyManagerUseCase applyManagerUseCase;

    @PostMapping("/super")
    public Long registerSuperManager(@RequestBody RequestManager requestManager) {
        return registerSuperManagerUseCase.execute(requestManager);
    }

    @PostMapping("/submission/shelters/{shelterUuid}")
    public Long ApplyManager(@PathVariable String shelterUuid,
                             @RequestBody RequestManager requestManager) {
        return applyManagerUseCase.execute(requestManager, shelterUuid);
    }
}
