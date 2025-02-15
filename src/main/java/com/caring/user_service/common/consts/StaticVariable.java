package com.caring.user_service.common.consts;

import com.caring.user_service.domain.authority.entity.ManagerRole;

import java.util.List;

public class StaticVariable {

    public final static String USER_MEMBER_CODE_PRESET = "CRU#";
    public final static String MANAGER_MEMBER_CODE_PRESET = "CRM#";
    public final static String GATEWAY_SERVICE_APPLICATION_NAME = "api-gateway-service";
    public final static String REQUEST_HEADER_MEMBER_CODE = "member-code";
    public final static String REQUEST_HEADER_ROLES = "roles";
    public final static List<ManagerRole> ALL_MANAGER_ACCESS = List.of(ManagerRole.MANAGE, ManagerRole.SUPER);

}
