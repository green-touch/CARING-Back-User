package com.caring.user_service.domain.shelter.business.validator;

import com.caring.user_service.common.AuthorityDataInitializer;
import com.caring.user_service.common.service.DatabaseCleanUp;
import com.caring.user_service.domain.authority.business.adaptor.AuthorityAdaptor;
import com.caring.user_service.domain.authority.entity.Authority;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.domain.manager.business.service.ManagerDomainService;
import com.caring.user_service.domain.manager.entity.Manager;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class ShelterValidatorTest {

    @Autowired
    ShelterValidator shelterValidator;
    @Autowired
    UserDomainService userDomainService;
    @Autowired
    ManagerDomainService managerDomainService;
    @Autowired
    ShelterDomainService shelterDomainService;
    @Autowired
    AuthorityAdaptor authorityAdaptor;
    @Autowired
    DatabaseCleanUp databaseCleanUp;
    @Autowired
    AuthorityDataInitializer authorityDataInitializer;

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
    @DisplayName("유저와 매니저의 쉘터가 같은지 확인합니다.")
    void isSameShelterUserAndManager(){
        //given
        User user = userDomainService.registerUser("name", "password");
        Authority authority = authorityAdaptor.queryByManagerRole(ManagerRole.SUPER);
        Manager manager = managerDomainService.registerManager("name", "password", authority);

        Shelter shelter = shelterDomainService.registerShelter("shelter", "location");
        shelterDomainService.addShelterStaff(shelter.getShelterUuid(), manager);
        shelterDomainService.addShelterGroup(shelter.getShelterUuid(), user);
        //when
        boolean result = shelterValidator.isSameShelterUserAndManager(user, manager);
        //then
        assertThat(result).isTrue();

    }

    @Test
    @Transactional
    @DisplayName("존재하는 쉘터 랜덤 난수인지 확인합니다. " +
            "(쉘터는 직접적인 연관관계를 맺어주는 것보다 랜덤난수를 fk처럼 사용하기 때문에 만든 메서드입니다.)")
    void isExistShelter(){
        //given
        Shelter shelter = shelterDomainService.registerShelter("shelter", "location");
        //when
        boolean result = shelterValidator.isExistShelter(shelter.getShelterUuid());
        //then
        assertThat(result).isTrue();
    }

}