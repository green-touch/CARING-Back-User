package com.caring.user_service.domain.manager.business.service;

import com.caring.user_service.common.annotation.DomainService;
import com.caring.user_service.common.consts.StaticVariable;
import com.caring.user_service.common.util.RandomNumberUtil;
import com.caring.user_service.domain.authority.entity.Authority;
import com.caring.user_service.domain.authority.entity.ManagerAuthority;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.domain.authority.repository.AuthorityRepository;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.manager.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import static com.caring.user_service.common.consts.StaticVariable.MANAGER_MEMBER_CODE_PRESET;
import static com.caring.user_service.common.util.RandomNumberUtil.generateRandomMemberCode;

@DomainService
@RequiredArgsConstructor
public class ManagerDomainServiceImpl implements ManagerDomainService{

    private final ManagerRepository managerRepository;

    @Override
    public Long registerManager(String name, String password, Authority authority) {
        Manager newManager = Manager.builder()
                .managerUuid(UUID.randomUUID().toString())
                .memberCode(generateRandomMemberCode(MANAGER_MEMBER_CODE_PRESET))
                .name(name)
                .password(password)
                .build();
        ManagerAuthority.builder()
                .manager(newManager)
                .authority(authority)
                .build()
                .link(newManager);

        return managerRepository.save(newManager).getId();
    }
}
