package com.caring.user_service.domain.user.business.validator;

import com.caring.user_service.domain.user.dao.entity.User;

public interface UserValidator {
    void checkPasswordEncode(User user, String password);
}
