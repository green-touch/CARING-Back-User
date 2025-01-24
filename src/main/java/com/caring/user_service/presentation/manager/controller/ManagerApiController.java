package com.caring.user_service.presentation.manager.controller;

import com.caring.user_service.presentation.manager.usecase.RegisterSuperManagerUseCase;
import com.caring.user_service.presentation.manager.vo.RequestManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/managers")
public class ManagerApiController {

    private final RegisterSuperManagerUseCase registerSuperManagerUseCase;

    @PostMapping
    public Long registerSuperManager(@RequestBody RequestManager requestManager) {
        return registerSuperManagerUseCase.execute(requestManager);
    }
}
