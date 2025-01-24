package com.caring.user_service.domain.manager.business.adaptor;

import com.caring.user_service.common.service.DatabaseCleanUp;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.manager.repository.ManagerRepository;
import com.caring.user_service.domain.shelter.entity.Shelter;
import com.caring.user_service.domain.shelter.entity.ShelterStaff;
import com.caring.user_service.domain.shelter.repository.ShelterRepository;
import com.caring.user_service.domain.shelter.repository.ShelterStaffRepository;
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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class ManagerAdaptorTest {

    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    ManagerAdaptor managerAdaptor;
    @Autowired
    ShelterRepository shelterRepository;
    @Autowired
    ShelterStaffRepository shelterStaffRepository;
    @Autowired
    DatabaseCleanUp databaseCleanUp;

    Manager manager1;
    Manager manager2;
    Shelter shelter;

    @BeforeEach
    void setup() {
        manager1 = Manager.builder()
                .name("manager1")
                .managerUuid(UUID.randomUUID().toString())
                .password("manager1")
                .memberCode("manager1")
                .build();
        manager2 = Manager.builder()
                .name("manager2")
                .managerUuid(UUID.randomUUID().toString())
                .password("manager2")
                .memberCode("manager2")
                .build();
        managerRepository.saveAll(List.of(manager1, manager2));
        shelter = Shelter.builder()
                .shelterUuid(UUID.randomUUID().toString())
                .name("shelter")
                .location("shelter")
                .build();
        shelterRepository.save(shelter);
        ShelterStaff shelterStaff_1 = ShelterStaff.builder()
                .shelter(shelter)
                .manager(manager1)
                .build();
        ShelterStaff shelterStaff_2 = ShelterStaff.builder()
                .shelter(shelter)
                .manager(manager2)
                .build();
        shelterStaffRepository.saveAll(List.of(shelterStaff_1, shelterStaff_2));
    }

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @DisplayName("MemberCode를 통해 Manager entity를 가져옵니다.")
    void queryByMemberCode(){
        //given
        //when
        Manager findManager = managerAdaptor.queryByMemberCode("manager1");
        //then
        assertThat(findManager.getName()).isEqualTo(manager1.getName());
    }
    @Test
    @DisplayName("Manager uuid를 통해 Manager entity를 가져옵니다.")
    void queryByManagerUuid(){
        //given
        //when
        Manager findManager = managerAdaptor.queryByManagerUuid(manager1.getManagerUuid());
        //then
        assertThat(findManager.getMemberCode()).isEqualTo(manager1.getMemberCode());
        assertThat(findManager).isEqualTo(manager1);
    }

    @Test
    @Transactional
    @DisplayName("보호소에 소속된 모든 manager들을 불러옵니다.")
    void queryByShelter(){
        //given

        //when
        List<Manager> managers = managerAdaptor.queryByShelter(shelter.getShelterUuid());
        //then
        assertThat(managers.size()).isEqualTo(2);
        assertThat(managers.get(0).getMemberCode()).isEqualTo(manager1.getMemberCode());
        assertThat(managers.get(1).getMemberCode()).isEqualTo(manager2.getMemberCode());
    }
  
}