package com.caring.user_service.domain.manager.business.service;

import com.caring.user_service.common.annotation.DomainService;
import com.caring.user_service.common.consts.StaticVariable;
import com.caring.user_service.common.util.RandomNumberUtil;
import com.caring.user_service.domain.authority.entity.Authority;
import com.caring.user_service.domain.authority.entity.ManagerAuthority;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.domain.authority.repository.AuthorityRepository;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.manager.entity.Submission;
import com.caring.user_service.domain.manager.repository.ManagerRepository;
import com.caring.user_service.domain.manager.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static com.caring.user_service.common.consts.StaticVariable.MANAGER_MEMBER_CODE_PRESET;
import static com.caring.user_service.common.util.RandomNumberUtil.generateRandomMemberCode;

@DomainService
@RequiredArgsConstructor
public class ManagerDomainServiceImpl implements ManagerDomainService{

    private final ManagerRepository managerRepository;
    private final SubmissionRepository submissionRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Manager registerManager(String name, String password, Authority authority) {
        Manager newManager = Manager.builder()
                .managerUuid(UUID.randomUUID().toString())
                .memberCode(generateRandomMemberCode(MANAGER_MEMBER_CODE_PRESET))
                .name(name)
                .password(passwordEncoder.encode(password))
                .build();
        ManagerAuthority.builder()
                .manager(newManager)
                .authority(authority)
                .build()
                .link(newManager);

        return managerRepository.save(newManager);
    }

    @Override
    public Submission applyManager(String name, String password, String shelterUuid) {
        Submission application = Submission.builder()
                .name(name)
                .password(passwordEncoder.encode(password))
                .shelterUuid(shelterUuid)
                .build();

        return submissionRepository.save(application);
    }
}
