package com.caring.user_service.domain.authority.entity;

import com.caring.user_service.domain.user.entity.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ManagerRole {
    MANAGE("ROLE_MANAGE"), SUPER("ROLE_SUPER");

    private final String key;
}
