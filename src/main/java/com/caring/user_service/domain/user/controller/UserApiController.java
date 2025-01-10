package com.caring.user_service.domain.user.controller;

import com.caring.user_service.domain.user.business.usecase.ReadAllUserUseCase;
import com.caring.user_service.domain.user.business.usecase.RegisterUserUseCase;
import com.caring.user_service.domain.user.vo.request.RequestUser;
import com.caring.user_service.domain.user.vo.response.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/users")
@RequiredArgsConstructor
public class UserApiController {

    private final RegisterUserUseCase registerUserUseCase;
    private final ReadAllUserUseCase readAllUserUseCase;

    @PostMapping
    public Long registerUser(@RequestBody RequestUser requestUser) {
        return registerUserUseCase.execute(requestUser);
    }

    @GetMapping
    public List<ResponseUser> getAllUser() {
        return readAllUserUseCase.execute();
    }
}
