package com.caring.user_service.domain.manager.entity;

import com.caring.user_service.common.interfaces.KeyedEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SubmissionStatus implements KeyedEnum {
    APPLY("APPLY"), PERMIT("PERMIT"), REJECTED("REJECTED");
    private final String key;
}
