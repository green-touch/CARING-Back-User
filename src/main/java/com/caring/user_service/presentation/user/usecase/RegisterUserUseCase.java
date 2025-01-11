package com.caring.user_service.presentation.user.usecase;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.user.business.domainservice.UserDomainService;
import com.caring.user_service.presentation.user.vo.request.RequestUser;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class RegisterUserUseCase {
    private final UserDomainService userDomainService;

    public Long execute(RequestUser requestUser) {
        return userDomainService.registerUser(requestUser.getPassword(), requestUser.getName());
    }
}
