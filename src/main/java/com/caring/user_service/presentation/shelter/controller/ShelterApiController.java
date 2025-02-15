package com.caring.user_service.presentation.shelter.controller;

import com.caring.user_service.common.annotation.ManagerCode;
import com.caring.user_service.common.annotation.MemberCode;
import com.caring.user_service.presentation.shelter.service.RegisterShelterUseCase;
import com.caring.user_service.presentation.shelter.vo.RequestShelter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/shelters")
public class ShelterApiController {

    private final RegisterShelterUseCase registerShelterUseCase;

    @PostMapping
    public Long registerShelter(@ManagerCode String memberCode,
                                @RequestBody RequestShelter requestShelter) {
        return registerShelterUseCase.execute(requestShelter, memberCode);
    }
}
