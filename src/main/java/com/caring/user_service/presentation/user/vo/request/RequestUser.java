package com.caring.user_service.presentation.user.vo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RequestUser {
    private final String password;
    private final String name;
}
