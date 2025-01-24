package com.caring.user_service.presentation.manager.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RequestManager {
    private final String name;
    private final String password;
}
