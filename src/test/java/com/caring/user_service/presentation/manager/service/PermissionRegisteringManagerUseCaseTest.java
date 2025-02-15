package com.caring.user_service.presentation.manager.service;

import com.caring.user_service.common.AuthorityDataInitializer;
import com.caring.user_service.common.service.DatabaseCleanUp;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.manager.repository.ManagerRepository;
import com.caring.user_service.domain.shelter.entity.Shelter;
import com.caring.user_service.domain.shelter.repository.ShelterRepository;
import com.caring.user_service.presentation.manager.vo.RequestManager;
import com.caring.user_service.presentation.manager.vo.ResponseSubmission;
import com.caring.user_service.presentation.shelter.service.RegisterShelterUseCase;
import com.caring.user_service.presentation.shelter.vo.RequestShelter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class PermissionRegisteringManagerUseCaseTest {
    @Autowired
    PermissionRegisteringManagerUseCase permissionRegisteringManagerUseCase;
    @Autowired
    GetPendingSubmissionsUseCase getPendingSubmissionsUseCase;
    @Autowired
    ApplyManagerUseCase applyManagerUseCase;
    @Autowired
    RegisterSuperManagerUseCase registerSuperManagerUseCase;
    @Autowired
    RegisterShelterUseCase registerShelterUseCase;
    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    ShelterRepository shelterRepository;
    @Autowired
    AuthorityDataInitializer authorityDataInitializer;
    @Autowired
    DatabaseCleanUp databaseCleanUp;

    Manager superManager;
    Shelter shelter;
    RequestManager requestManager1;
    RequestManager requestManager2;


    @BeforeEach
    void setup() {
        authorityDataInitializer.initAuthorityData();
        Long superManagerId = registerSuperManagerUseCase.execute(
                RequestManager.builder()
                        .name("super")
                        .password("super")
                        .build());
        superManager = managerRepository.findById(superManagerId).orElseThrow();
        requestManager1 = RequestManager.builder()
                .name("manager1")
                .password("manager1")
                .build();
        requestManager2 = RequestManager.builder()
                .name("manager2")
                .password("manager2")
                .build();
        RequestShelter requestShelter = RequestShelter.builder()
                .name("name")
                .location("location")
                .build();

        Long shelterId = registerShelterUseCase.execute(requestShelter, superManager.getMemberCode());
        shelter = shelterRepository.findById(shelterId).orElseThrow();

    }
    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @Transactional
    @DisplayName("[매니저 신청 -> 조회 -> 허가] 의 플로우를 한번에 보입니다.")
    void setPermissionRegisteringManagerUseCase(){
        //given
        applyManagerUseCase.execute(requestManager1, shelter.getShelterUuid());
        applyManagerUseCase.execute(requestManager2, shelter.getShelterUuid());
        List<ResponseSubmission> execute = getPendingSubmissionsUseCase.execute();
        for (ResponseSubmission responseSubmission : execute) {
            log.info("submission = {}", responseSubmission.getSubmissionUuid());
        }
        //when
        permissionRegisteringManagerUseCase.execute(execute.get(0).getSubmissionUuid(), superManager.getMemberCode());
        //then
        List<Manager> all = managerRepository.findAll();
        assertThat(all.size()).isEqualTo(2);
        assertThat(all.get(1).getManagerAuthorityList().get(0).getAuthority().getManagerRole()).isEqualTo(ManagerRole.MANAGE);
    }

}