package com.caring.user_service.domain.user.business.domainservice;

import com.caring.user_service.common.AuthorityDataInitializer;
import com.caring.user_service.common.service.DatabaseCleanUp;
import com.caring.user_service.domain.authority.business.adaptor.AuthorityAdaptor;
import com.caring.user_service.domain.authority.entity.Authority;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.domain.manager.business.service.ManagerDomainService;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.manager.entity.ManagerGroup;
import com.caring.user_service.domain.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class UserDomainServiceTest {

    @Autowired
    UserDomainService userDomainService;
    @Autowired
    ManagerDomainService managerDomainService;
    @Autowired
    AuthorityAdaptor authorityAdaptor;
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
    @DisplayName("이름과 패스워드를 입력하여 유저를 등록합니다.")
    void registerUser(){
        //given
        String name = "name";
        String password = "password";
        //when
        User user = userDomainService.registerUser(name, password);
        //then
        assertThat(name).isEqualTo(user.getName());
    }

    @Test
    @DisplayName("유저를 특정한 매니저의 소속이 될 수 있게 ManagerGroup엔티티를 만들어줍니다.")
    void addUserInManagerGroup(){
        //given
        String name = "name";
        String password = "password";
        User user = userDomainService.registerUser(name, password);
        Authority superAuthority = authorityAdaptor.queryByManagerRole(ManagerRole.SUPER);
        Manager manager = managerDomainService.registerManager(name, password, superAuthority);
        //when
        ManagerGroup managerGroup = userDomainService.addUserInManagerGroup(user, manager);
        //then
        assertThat(managerGroup.getManager()).isEqualTo(manager);
        assertThat(managerGroup.getUser()).isEqualTo(user);
    }

}