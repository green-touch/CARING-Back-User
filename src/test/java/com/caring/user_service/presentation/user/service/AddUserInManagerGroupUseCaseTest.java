package com.caring.user_service.presentation.user.service;

import com.caring.user_service.common.AuthorityDataInitializer;
import com.caring.user_service.common.service.DatabaseCleanUp;
import com.caring.user_service.domain.authority.business.adaptor.AuthorityAdaptor;
import com.caring.user_service.domain.authority.entity.Authority;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.domain.manager.business.service.ManagerDomainService;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.manager.entity.ManagerGroup;
import com.caring.user_service.domain.manager.repository.ManagerGroupRepository;
import com.caring.user_service.domain.shelter.business.service.ShelterDomainService;
import com.caring.user_service.domain.shelter.entity.Shelter;
import com.caring.user_service.domain.user.business.domainservice.UserDomainService;
import com.caring.user_service.domain.user.entity.User;
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
class AddUserInManagerGroupUseCaseTest {

    @Autowired
    AddUserInManagerGroupUseCase addUserInManagerGroupUseCase;
    @Autowired
    UserDomainService userDomainService;
    @Autowired
    ManagerDomainService managerDomainService;
    @Autowired
    AuthorityDataInitializer authorityDataInitializer;
    @Autowired
    AuthorityAdaptor authorityAdaptor;
    @Autowired
    ManagerGroupRepository managerGroupRepository;
    @Autowired
    ShelterDomainService shelterDomainService;
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
    @DisplayName("유저를 특정 매니저에 소속시킵니다. 이때 유저는 여러 매니저에게 소속될 수 있습니다.")
    void addUserInManagerGroupUseCase(){
        //given
        User user = userDomainService.registerUser("name", "password");
        Authority superAuthority = authorityAdaptor.queryByManagerRole(ManagerRole.SUPER);
        Manager manager = managerDomainService
                .registerManager("manager_name", "manager_password", superAuthority);
        Shelter shelter = shelterDomainService.registerShelter("shelter", "location");
        shelterDomainService.addShelterGroup(shelter.getShelterUuid(), user);
        shelterDomainService.addShelterStaff(shelter.getShelterUuid(), manager);
        //when
        Long managerGroupId = addUserInManagerGroupUseCase.execute(user.getUserUuid(), manager.getManagerUuid());
        //then
        Optional<ManagerGroup> managerGroup = managerGroupRepository.findById(managerGroupId);
        assertThat(managerGroup).isPresent();
        assertThat(managerGroup.get().getManager()).isEqualTo(manager);
        assertThat(managerGroup.get().getUser()).isEqualTo(user);
    }

}