package com.caring.user_service.presentation.manager.service;

import com.caring.user_service.common.AuthorityDataInitializer;
import com.caring.user_service.common.service.DatabaseCleanUp;
import com.caring.user_service.domain.manager.repository.ManagerRepository;
import com.caring.user_service.domain.shelter.entity.Shelter;
import com.caring.user_service.domain.shelter.repository.ShelterRepository;
import com.caring.user_service.presentation.manager.vo.request.RequestManager;
import com.caring.user_service.presentation.manager.vo.response.ResponseSubmission;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class GetPendingSubmissionsUseCaseTest {

    @Autowired
    RegisterSuperManagerUseCase registerSuperManagerUseCase;
    @Autowired
    GetPendingSubmissionsUseCase getPendingSubmissionsUseCase;
    @Autowired
    ApplyManagerUseCase applyManagerUseCase;
    @Autowired
    RegisterShelterUseCase registerShelterUseCase;
    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    ShelterRepository shelterRepository;
    @Autowired
    DatabaseCleanUp databaseCleanUp;
    @Autowired
    AuthorityDataInitializer authorityDataInitializer;

    RequestManager super_request;
    RequestManager request1;
    RequestManager request2;
    RequestManager request3;
    RequestShelter requestShelter;


    @BeforeEach
    void setup() {
        authorityDataInitializer.initAuthorityData();
        super_request = RequestManager.builder()
                .name("super")
                .password("password1")
                .build();
        request1 = RequestManager.builder()
                .name("name1")
                .password("password1")
                .build();
        request2 = RequestManager.builder()
                .name("name2")
                .password("password2")
                .build();
        request3 = RequestManager.builder()
                .name("name3")
                .password("password3")
                .build();
        requestShelter = RequestShelter.builder()
                .name("name")
                .location("location")
                .build();
    }


    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @DisplayName("아직 허가가 되지 않은 매니저 신청들을 조회합니다.")
    void setGetPendingSubmissionsUseCase(){
        //given
        Long superManagerId = registerSuperManagerUseCase.execute(super_request);
        Long shelterId = registerShelterUseCase.execute(
                requestShelter,
                managerRepository.findById(superManagerId).orElseThrow().getMemberCode()
        );
        Shelter shelter = shelterRepository.findById(shelterId).orElseThrow();
        applyManagerUseCase.execute(request1, shelter.getShelterUuid());
        applyManagerUseCase.execute(request2, shelter.getShelterUuid());
        applyManagerUseCase.execute(request3, shelter.getShelterUuid());
        //when
        List<ResponseSubmission> execute = getPendingSubmissionsUseCase.execute();
        //then
        assertThat(execute.size()).isEqualTo(3);
        assertThat(execute.get(0).getShelterUuid()).isEqualTo(shelter.getShelterUuid());
    }

}