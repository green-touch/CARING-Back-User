package com.caring.user_service.presentation.user.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUserUuid {
    private Long userId;
    private String userUuid;
}

