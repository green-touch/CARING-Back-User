package com.caring.user_service.presentation.user.controller;

import com.caring.user_service.common.annotation.ManagerRoles;
import com.caring.user_service.common.util.RoleUtil;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.presentation.user.service.AddUserInManagerGroupUseCase;
import com.caring.user_service.presentation.user.service.ReadAllUserUseCase;
import com.caring.user_service.presentation.user.service.RegisterUserByManagerUseCase;
import com.caring.user_service.presentation.user.service.RegisterUserUseCase;
import com.caring.user_service.presentation.user.vo.RequestUser;
import com.caring.user_service.presentation.user.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/users")
@RequiredArgsConstructor
public class UserApiController {

    private final RegisterUserByManagerUseCase registerUserByManagerUseCase;
    private final AddUserInManagerGroupUseCase addUserInManagerGroupUseCase;
//    private final GetUserProfileUseCase getUserProfileUseCase;


    @PostMapping("/shelters/{shelterUuid}")
    public Long registerUserByManager(@PathVariable String shelterUuid,
                                      @RequestBody RequestUser requestUser,
                                      @ManagerRoles List<String> roles) {
        RoleUtil.containManagerRole(ManagerRole.SUPER, roles);
        return registerUserByManagerUseCase.execute(requestUser, shelterUuid);
    }

    @PostMapping("/{userUuid}/managers/{managerUuid}")
    public Long addUserInManagerGroup(@PathVariable String userUuid,
                                      @PathVariable String managerUuid,
                                      @ManagerRoles List<String> roles) {
        RoleUtil.containManagerRole(ManagerRole.SUPER, roles);
        return addUserInManagerGroupUseCase.execute(userUuid, managerUuid);
    }

//    @GetMapping("/{userUuid}")
//    public ResponseUser getUserProfile(@PathVariable String userUuid,
//                                       @MemberCode String memberCode) {
//        return getUserProfileUseCase.execute(userUuid, memberCode);
//    }
}
