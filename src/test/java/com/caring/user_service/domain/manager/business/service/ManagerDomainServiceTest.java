package com.caring.user_service.domain.manager.business.service;

import com.caring.user_service.common.AuthorityDataInitializer;
import com.caring.user_service.common.service.DatabaseCleanUp;
import com.caring.user_service.domain.authority.business.adaptor.AuthorityAdaptor;
import com.caring.user_service.domain.authority.entity.Authority;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.manager.repository.ManagerRepository;
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
class ManagerDomainServiceTest {

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
    @DisplayName("이름, 비밀번호, 권한을 통해서 매니저를 등록합니다.")
    void registerManager(){
        //given
        String name = "manager";
        String password = "password";
        Authority superAuthority = authorityAdaptor.queryByManagerRole(ManagerRole.SUPER);
        //when
        Long managerId = managerDomainService.registerManager(name, password, superAuthority);
        //then
        Optional<Manager> findManager = managerRepository.findById(managerId);
        assertThat(findManager).isPresent();
        assertThat(findManager.get().getName()).isEqualTo(name);
        assertThat(findManager.get().getPassword()).isEqualTo(password);
        log.info("findManager.memberCode = {}", findManager.get().getMemberCode());
    }
}