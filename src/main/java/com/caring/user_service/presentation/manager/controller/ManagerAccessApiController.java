package com.caring.user_service.presentation.manager.controller;

import com.caring.user_service.presentation.manager.service.ApplyManagerUseCase;
import com.caring.user_service.presentation.manager.service.RegisterSuperManagerUseCase;
import com.caring.user_service.presentation.manager.vo.RequestManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/access/managers")
public class ManagerAccessApiController {

    private final RegisterSuperManagerUseCase registerSuperManagerUseCase;
    private final ApplyManagerUseCase applyManagerUseCase;

    @PostMapping("/super")
    public Long registerSuperManager(@RequestBody RequestManager requestManager) {
        return registerSuperManagerUseCase.execute(requestManager);
    }

    @PostMapping("/submissions/shelters/{uuid}")
    public Long applyManager(@PathVariable String uuid,
                             @RequestBody RequestManager requestManager) {
        return applyManagerUseCase.execute(requestManager, uuid);
    }
}
