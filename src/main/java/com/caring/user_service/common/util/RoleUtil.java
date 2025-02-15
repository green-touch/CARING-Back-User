package com.caring.user_service.common.util;

import com.caring.user_service.domain.authority.entity.ManagerRole;

import java.util.List;

public class RoleUtil {

    public static void containManagerRole(ManagerRole managerRole, List<String> roles) {
        if (roles.stream()
                .map(role -> EnumConvertUtil.convert(ManagerRole.class, role))
                .noneMatch(managerRole::equals)) {
            throw new RuntimeException("No matching role found");
        }
    }

    public static void containManagerRole(List<ManagerRole> managerRoles, List<String> roles) {
        if (roles.stream()
                .map(role -> EnumConvertUtil.convert(ManagerRole.class, role))
                .noneMatch(managerRoles::contains)
        ) {
            throw new RuntimeException("No matching role found");
        }
    }
}
