package com.caring.user_service.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    USER("ROLE_USER"), NOT_ALLOWED("ROLE_NOT_ALLOWED");

    private final String key;

    public static Role converter(String key) {
        return key.equals(USER.getKey())? USER : NOT_ALLOWED;
    }

}
