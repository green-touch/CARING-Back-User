package com.caring.user_service.presentation.manager.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class RequestManager {
    private final String name;
    private final String password;
}
