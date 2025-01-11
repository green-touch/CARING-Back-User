package com.caring.user_service.presentation.user.vo.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class ResponseUser {
    private final String name;
    private final String userUuid;
    private final String userNumber;
}
