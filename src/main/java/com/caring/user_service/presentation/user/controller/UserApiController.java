package com.caring.user_service.presentation.user.controller;

import com.caring.user_service.common.annotation.ManagerCode;
import com.caring.user_service.common.annotation.ManagerRoles;
import com.caring.user_service.common.consts.StaticVariable;
import com.caring.user_service.common.util.RoleUtil;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.presentation.user.service.*;
import com.caring.user_service.presentation.user.vo.RequestUser;
import com.caring.user_service.presentation.user.vo.ResponseUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "[회원(AUTH)]")
@Slf4j
@RestController
@RequestMapping("/v1/api/users")
@RequiredArgsConstructor
public class UserApiController {

    private final RegisterUserByManagerUseCase registerUserByManagerUseCase;
    private final AddUserInManagerGroupUseCase addUserInManagerGroupUseCase;
    private final GetUserProfileUseCase getUserProfileUseCase;
    private final GetUsersOfManagerGroupUseCase getUsersOfManagerGroupUseCase;

    @Operation(summary = "매니저가 유저 계정을 서버에 등록합니다. 이때 매니저는 SUPER 권한을 가지고 있어야합니다.")
    @PostMapping("/shelters/{shelterUuid}")
    public Long registerUserByManager(@PathVariable String shelterUuid,
                                      @RequestBody RequestUser requestUser,
                                      @ManagerRoles List<String> roles) {
        RoleUtil.containManagerRole(ManagerRole.SUPER, roles);
        return registerUserByManagerUseCase.execute(requestUser, shelterUuid);
    }

    @Operation(summary = "유저를 매니저 관리 관할에 소속시킵니다.")
    @PostMapping("/{userUuid}/managers/{managerUuid}")
    public Long addUserInManagerGroup(@PathVariable String userUuid,
                                      @PathVariable String managerUuid,
                                      @ManagerRoles List<String> roles) {
        RoleUtil.containManagerRole(ManagerRole.SUPER, roles);
        return addUserInManagerGroupUseCase.execute(userUuid, managerUuid);
    }

    @Operation(summary = "특정 유저의 계정을 조회합니다.")
    @GetMapping("/{userUuid}")
    public ResponseUser getUserProfile(@PathVariable String userUuid,
                                       @ManagerRoles List<String> roles) {
        RoleUtil.containManagerRole(StaticVariable.ALL_MANAGER_ACCESS, roles);
        return getUserProfileUseCase.execute(userUuid);
    }

    @Operation(summary = "로그인 중인 매니저의 관리 유저들을 조회합니다.")
    @GetMapping("/grouped")
    public List<ResponseUser> getUsersOfManagerGroup(@ManagerCode String memberCode) {
        return getUsersOfManagerGroupUseCase.execute(memberCode);
    }
}
