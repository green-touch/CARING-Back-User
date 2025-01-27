package com.caring.user_service.domain.shelter.business.service;

import com.caring.user_service.common.AuthorityDataInitializer;
import com.caring.user_service.common.service.DatabaseCleanUp;
import com.caring.user_service.domain.authority.business.adaptor.AuthorityAdaptor;
import com.caring.user_service.domain.authority.entity.Authority;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.domain.manager.business.service.ManagerDomainService;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.shelter.business.adaptor.ShelterAdaptor;
import com.caring.user_service.domain.shelter.entity.Shelter;
import com.caring.user_service.domain.shelter.entity.ShelterStaff;
import com.caring.user_service.domain.shelter.repository.ShelterRepository;
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
class ShelterDomainServiceTest {

    @Autowired
    ShelterDomainService shelterDomainService;
    @Autowired
    ShelterRepository shelterRepository;
    @Autowired
    ManagerDomainService managerDomainService;
    @Autowired
    AuthorityDataInitializer authorityDataInitializer;
    @Autowired
    AuthorityAdaptor authorityAdaptor;
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
    @DisplayName("보호소의 이름과 위치를 작성하여 shelter 데이터를 입력합니다.")
    void registerShelter(){
        //given
        String name = "shelter";
        String location = "location";
        //when
        Long shelterId = shelterDomainService.registerShelter(name, location).getId();
        //then
        Optional<Shelter> findShelter = shelterRepository.findById(shelterId);
        assertThat(findShelter).isPresent();
        assertThat(findShelter.get().getName()).isEqualTo(name);
    }

    @Test
    @Transactional
    @DisplayName("데이터베이스에 저장된 매니저와 보호소를 엮어 매니저가 보호소 소속이 될 수 있도록 해줍니다.")
    void addShelterStaff(){
        //given
        Authority authority = authorityAdaptor.queryByManagerRole(ManagerRole.MANAGE);
        Shelter shelter = shelterDomainService.registerShelter("shelter", "location");
        Manager manager = managerDomainService.registerManager("manager", "password", authority);
        //when
        ShelterStaff shelterStaff = shelterDomainService.addShelterStaff(shelter, manager);
        //then
        assertThat(shelterStaff.getShelter().getShelterUuid()).isEqualTo(shelter.getShelterUuid());
        assertThat(shelterStaff.getManager().getManagerUuid()).isEqualTo(manager.getManagerUuid());
    }

}