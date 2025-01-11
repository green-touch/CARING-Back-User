package com.caring.user_service.domain.user.business.adaptor;

import com.caring.user_service.domain.user.entity.User;

import java.util.List;

public interface UserAdaptor {

    User findById(Long userId);

    List<User> findAll();

    User getUserByMemberCode(String memberCode);
}
