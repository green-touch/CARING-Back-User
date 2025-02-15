package com.caring.user_service.presentation.user.controller;

import com.caring.user_service.common.annotation.MemberCode;
import com.caring.user_service.common.annotation.Roles;
import com.caring.user_service.common.util.EnumConvertUtil;
import com.caring.user_service.common.util.RoleUtil;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.presentation.user.usecase.AddUserInManagerGroupUseCase;
import com.caring.user_service.presentation.user.usecase.ReadAllUserUseCase;
import com.caring.user_service.presentation.user.usecase.RegisterUserByManagerUseCase;
import com.caring.user_service.presentation.user.usecase.RegisterUserUseCase;
import com.caring.user_service.presentation.user.vo.RequestUser;
import com.caring.user_service.presentation.user.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.util.EnumUtils;

import java.util.List;

@RestController
@RequestMapping("/v1/api/users")
@RequiredArgsConstructor
public class UserApiController {

    private final RegisterUserUseCase registerUserUseCase;
    private final ReadAllUserUseCase readAllUserUseCase;
    private final RegisterUserByManagerUseCase registerUserByManagerUseCase;
    private final AddUserInManagerGroupUseCase addUserInManagerGroupUseCase;
//    private final GetUserProfileUseCase getUserProfileUseCase;

    @PostMapping
    public Long registerUser(@RequestBody RequestUser requestUser) {
        return registerUserUseCase.execute(requestUser);
    }

    @GetMapping
    public List<ResponseUser> getAllUser() {
        return readAllUserUseCase.execute();
    }

    @PostMapping("/shelters/{shelterUuid}")
    public Long registerUserByManager(@PathVariable String shelterUuid,
                                      @RequestBody RequestUser requestUser,
                                      @Roles List<String> roles) {
        RoleUtil.containManagerRole(ManagerRole.SUPER, roles);
        return registerUserByManagerUseCase.execute(requestUser, shelterUuid);
    }

    @PostMapping("/{userUuid}/managers/{managerUuid}")
    public Long addUserInManagerGroup(@PathVariable String userUuid,
                                      @PathVariable String managerUuid,
                                      @Roles List<String> roles) {
        RoleUtil.containManagerRole(ManagerRole.SUPER, roles);
        return addUserInManagerGroupUseCase.execute(userUuid, managerUuid);
    }

//    @GetMapping("/{userUuid}")
//    public ResponseUser getUserProfile(@PathVariable String userUuid,
//                                       @MemberCode String memberCode) {
//        return getUserProfileUseCase.execute(userUuid, memberCode);
//    }
}
