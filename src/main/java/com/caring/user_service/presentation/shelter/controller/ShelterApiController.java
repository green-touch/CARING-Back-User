package com.caring.user_service.presentation.shelter.controller;

import com.caring.user_service.common.annotation.AuthManager;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.presentation.shelter.usecase.RegisterShelterUseCase;
import com.caring.user_service.presentation.shelter.vo.RequestShelter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/shelter")
public class ShelterApiController {

    private final RegisterShelterUseCase registerShelterUseCase;

    @PostMapping
    public Long registerShelter(@AuthManager Manager manager,
                                @RequestBody RequestShelter requestShelter) {
        return registerShelterUseCase.execute(requestShelter, manager);
    }
}
