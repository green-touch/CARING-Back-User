package com.caring.user_service.domain.authority.entity;

import com.caring.user_service.common.interfaces.KeyedEnum;
import com.caring.user_service.domain.user.entity.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ManagerRole implements KeyedEnum {
    MANAGE("ROLE_MANAGE"), SUPER("ROLE_SUPER");

    private final String key;
}
