package com.caring.user_service.presentation.user.usecase;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.user.business.adaptor.UserAdaptor;
import com.caring.user_service.domain.user.business.validator.UserValidator;
import com.caring.user_service.domain.user.entity.User;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UserLoginUseCase {
    private final UserAdaptor userAdaptor;
    private final UserValidator userValidator;

    public User execute(String userNumber, String password) {
        User findUser = userAdaptor.getUserByMemberCode(userNumber);
        userValidator.checkPasswordEncode(findUser, password);
        return findUser;
    }
}
