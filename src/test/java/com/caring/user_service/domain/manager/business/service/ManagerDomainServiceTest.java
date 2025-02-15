package com.caring.user_service.domain.manager.business.service;

import com.caring.user_service.common.AuthorityDataInitializer;
import com.caring.user_service.common.service.DatabaseCleanUp;
import com.caring.user_service.domain.authority.business.adaptor.AuthorityAdaptor;
import com.caring.user_service.domain.authority.entity.Authority;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.manager.entity.Submission;
import com.caring.user_service.domain.manager.entity.SubmissionStatus;
import com.caring.user_service.domain.manager.repository.ManagerRepository;
import com.caring.user_service.domain.manager.repository.SubmissionRepository;
import com.caring.user_service.domain.shelter.business.service.ShelterDomainService;
import com.caring.user_service.domain.shelter.entity.Shelter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static com.caring.user_service.domain.manager.entity.SubmissionStatus.APPLY;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class ManagerDomainServiceTest {

    @Autowired
    ManagerDomainService managerDomainService;
    @Autowired
    ShelterDomainService shelterDomainService;
    @Autowired
    AuthorityAdaptor authorityAdaptor;
    @Autowired
    AuthorityDataInitializer authorityDataInitializer;
    @Autowired
    SubmissionRepository submissionRepository;
    @Autowired
    DatabaseCleanUp databaseCleanUp;

    @BeforeEach
    void setup() {
        authorityDataInitializer.initAuthorityData();
    }

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @Transactional
    @DisplayName("이름, 비밀번호, 권한을 통해서 매니저를 등록합니다.")
    void registerManager(){
        //given
        String name = "manager";
        String password = "password";
        Authority superAuthority = authorityAdaptor.queryByManagerRole(ManagerRole.SUPER);
        //when
        Manager findManager = managerDomainService.registerManager(name, password, superAuthority);
        //then
        assertThat(findManager.getName()).isEqualTo(name);
        log.info("findManager.memberCode = {}", findManager.getMemberCode());
    }

    @Test
    @Transactional
    @DisplayName("매니저를 신청합니다. 이때 super등록과는 다르게 서버에 존재하는 보호소의 랜덤 uuid를 같이 저장합니다.")
    void applyManager() {
        //given
        Shelter shelter = shelterDomainService.registerShelter("shelter", "location");
        //when
        Submission submission = managerDomainService.applyManager("name", "password", shelter.getShelterUuid());
        //then
        assertThat(submission.getShelterUuid()).isEqualTo(shelter.getShelterUuid());
        assertThat(submission.getName()).isEqualTo("name");

    }

    @Test
    @Transactional
    @DisplayName("submission을 지웁니다.(보통 매니저 신청 허가에 의해서 사용됩니다.)")
    void removeSubmission(){
        //given
        Shelter shelter = shelterDomainService.registerShelter("shelter", "location");
        Submission submission = managerDomainService
                .applyManager("name", "password", shelter.getShelterUuid());
        assertThat(submission.getId()).isNotNull();
        //when
        managerDomainService.removeSubmission(submission.getSubmissionUuid());
        //then
        assertThat(submissionRepository.findAll().size()).isZero();
    }

}