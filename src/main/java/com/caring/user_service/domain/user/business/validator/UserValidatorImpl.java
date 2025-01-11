package com.caring.user_service.domain.user.business.validator;

import com.caring.user_service.common.annotation.Validator;
import com.caring.user_service.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Validator
@RequiredArgsConstructor
public class UserValidatorImpl implements UserValidator{
    private final PasswordEncoder passwordEncoder;

    /**
     * only use in filter, so need to throw filterException
     * @param user
     * @param password
     */
    @Override
    public void checkPasswordEncode(User user, String password) {
        if(!passwordEncoder.matches(password, user.getPassword()))
            throw new IllegalArgumentException("not match password");
    }
}
