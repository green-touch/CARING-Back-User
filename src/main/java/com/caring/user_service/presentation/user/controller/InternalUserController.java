package com.caring.user_service.presentation.user.controller;

import com.caring.user_service.presentation.user.service.GetUserShelterUuidUseCase;
import com.caring.user_service.presentation.user.service.RegisterUserUseCase;
import com.caring.user_service.presentation.user.vo.RequestUser;
import com.caring.user_service.presentation.user.vo.ResponseUserShelterUuid;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
@RequestMapping("/internal/users")
@RequiredArgsConstructor
public class InternalUserController {

    private final RegisterUserUseCase registerUserUseCase;
    private final GetUserShelterUuidUseCase getUserShelterUuidUseCase;

    @PostMapping("/register")
    public Long registerUser(@RequestBody RequestUser request) {
        return registerUserUseCase.execute(request);
    }

    @GetMapping("/{userUuid}/shelterUuid")
    public ResponseUserShelterUuid getUserShelterUuid(@PathVariable String userUuid) {
        return getUserShelterUuidUseCase.execute(userUuid);
    }
}
