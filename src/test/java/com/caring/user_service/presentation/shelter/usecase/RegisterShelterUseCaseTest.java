package com.caring.user_service.presentation.shelter.usecase;

import com.caring.user_service.common.AuthorityDataInitializer;
import com.caring.user_service.common.service.DatabaseCleanUp;
import com.caring.user_service.domain.authority.business.adaptor.AuthorityAdaptor;
import com.caring.user_service.domain.authority.entity.Authority;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.domain.manager.business.adaptor.ManagerAdaptor;
import com.caring.user_service.domain.manager.business.service.ManagerDomainService;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.manager.repository.ManagerRepository;
import com.caring.user_service.domain.shelter.entity.Shelter;
import com.caring.user_service.domain.shelter.repository.ShelterRepository;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class RegisterShelterUseCaseTest {

    @Autowired
    RegisterShelterUseCase registerShelterUseCase;
    @Autowired
    ShelterRepository shelterRepository;
    @Autowired
    ManagerDomainService managerDomainService;
    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    AuthorityAdaptor authorityAdaptor;
    @Autowired
    AuthorityDataInitializer authorityDataInitializer;
    @Autowired
    DatabaseCleanUp databaseCleanUp;

    @BeforeEach
    void setup() {
        authorityDataInitializer.initAuthorityData();;
    }

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @Transactional
    @DisplayName("manager와 shelter request를 통하여 서버에 shelter를 등록합니다. 이때 등록자는 SUPER 권한을 가지고 있어야합니다.")
    void setRegisterShelterUseCase(){
        //given
        RequestShelter request = RequestShelter.builder()
                .name("name")
                .location("location")
                .build();

        Authority superAuthority = authorityAdaptor.queryByManagerRole(ManagerRole.SUPER);
        Long managerId = managerDomainService.registerManager("name", "password", superAuthority);
        Manager manager = managerRepository.findById(managerId).orElseThrow();
        //when
        Long shelterId = registerShelterUseCase.execute(request, manager);
        //then
        Optional<Shelter> findShelter = shelterRepository.findById(shelterId);
        assertThat(findShelter).isPresent();
        assertThat(findShelter.get().getShelterUuid()).isNotNull();
        assertThat(findShelter.get().getName()).isEqualTo(request.getName());
        assertThat(findShelter.get().getLocation()).isEqualTo(request.getLocation());
    }

}