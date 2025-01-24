package com.caring.user_service.domain.manager.business.validate;

import com.caring.user_service.common.service.DatabaseCleanUp;
import com.caring.user_service.domain.authority.business.adaptor.AuthorityAdaptor;
import com.caring.user_service.domain.authority.entity.Authority;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.domain.manager.business.service.ManagerDomainService;
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
import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class ManagerValidatorTest {
    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    ManagerDomainService managerDomainService;
    @Autowired
    ManagerValidator managerValidator;
    @Autowired
    AuthorityAdaptor authorityAdaptor;
    @Autowired
    DatabaseCleanUp databaseCleanUp;

    Long managerId;

    @BeforeEach
    void setup() {
        Authority authority = authorityAdaptor.queryByManagerRoleKey(ManagerRole.MANAGE.getKey());
        managerId = managerDomainService.registerManager("name", "password", authority);
    }

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @Transactional
    @DisplayName("manager가 가진 authority에 super가 포함되어있는지 확인한다.")
    void isSuper(){
        //given
        Manager findManager = managerRepository.findById(managerId)
                .orElseThrow();
        //when
        boolean result = managerValidator.isSuper(findManager);
        //then
        assertThat(result).isFalse();
    }

}