package com.caring.user_service.presentation.security.controller;

import com.caring.user_service.presentation.security.service.user.UserTokenService;
import com.caring.user_service.presentation.security.vo.JwtToken;
import com.caring.user_service.presentation.security.vo.RequestLogin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/api/tokens")
@RequiredArgsConstructor
public class SecurityApiController {

    private final UserTokenService userTokenService;

    @PostMapping("/users")
    public JwtToken loginUser(@RequestBody RequestLogin requestLogin) {
        return userTokenService.login(requestLogin.getMemberCode(), requestLogin.getPassword());
    }
    

}
