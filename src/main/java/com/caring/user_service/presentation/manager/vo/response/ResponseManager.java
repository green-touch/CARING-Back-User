package com.caring.user_service.presentation.manager.vo.response;

import com.caring.user_service.domain.authority.entity.ManagerRole;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class ResponseManager {

    private final String managerUuid;
    private final String name;
    private final String shelterUuid;

}
