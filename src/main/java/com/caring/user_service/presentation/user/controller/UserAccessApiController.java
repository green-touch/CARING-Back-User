package com.caring.user_service.presentation.user.controller;

import com.caring.user_service.presentation.user.service.ReadAllUserUseCase;
import com.caring.user_service.presentation.user.service.RegisterUserUseCase;
import com.caring.user_service.presentation.user.vo.RequestUser;
import com.caring.user_service.presentation.user.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/access/users")
@RequiredArgsConstructor
public class UserAccessApiController {

    private final RegisterUserUseCase registerUserUseCase;
    private final ReadAllUserUseCase readAllUserUseCase;

//    @PostMapping
//    public Long registerUser(@RequestBody RequestUser requestUser) {
//        return registerUserUseCase.execute(requestUser);
//    }
//
//    @GetMapping
//    public List<ResponseUser> getAllUser() {
//        return readAllUserUseCase.execute();
//    }
}
