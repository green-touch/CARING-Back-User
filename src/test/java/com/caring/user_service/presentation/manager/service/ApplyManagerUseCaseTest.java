package com.caring.user_service.presentation.manager.service;

import com.caring.user_service.common.AuthorityDataInitializer;
import com.caring.user_service.common.service.DatabaseCleanUp;
import com.caring.user_service.domain.manager.entity.Submission;
import com.caring.user_service.domain.manager.repository.SubmissionRepository;
import com.caring.user_service.domain.shelter.business.service.ShelterDomainService;
import com.caring.user_service.domain.shelter.entity.Shelter;
import com.caring.user_service.presentation.manager.vo.request.RequestManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class ApplyManagerUseCaseTest {

    @Autowired
    ApplyManagerUseCase applyManagerUseCase;
    @Autowired
    ShelterDomainService shelterDomainService;
    @Autowired
    SubmissionRepository submissionRepository;
    @Autowired
    AuthorityDataInitializer authorityDataInitializer;
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
    @DisplayName("일반 매니저를 서버에 처음 등록할 때 필수 데이터를 submission이라는 임시 데이터에 저장합니다." +
            "이렇게 저장된 submission은 permit이 되었을 때 저장할 수 있도록 합니다.")
    void setApplyManagerUseCase(){
        //given
        RequestManager request = RequestManager.builder()
                .name("name")
                .password("password")
                .build();
        Shelter shelter = shelterDomainService.registerShelter("name", "location");
        //when
        Long submissionId = applyManagerUseCase.execute(request, shelter.getShelterUuid());
        //then
        Optional<Submission> findSubmission = submissionRepository.findById(submissionId);
        assertThat(findSubmission).isPresent();
        assertThat(findSubmission.get().getShelterUuid()).isEqualTo(shelter.getShelterUuid());
    }


}