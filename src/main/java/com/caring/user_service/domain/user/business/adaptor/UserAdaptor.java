package com.caring.user_service.domain.user.business.adaptor;

import com.caring.user_service.domain.user.dao.entity.User;

import java.util.List;

public interface UserAdaptor {

    User findById(Long userId);

    List<User> findAll();

    User getUserByUserNumber(String userNumber);
}
