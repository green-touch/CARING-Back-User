package com.caring.user_service.presentation.manager.service;

import com.caring.user_service.common.AuthorityDataInitializer;
import com.caring.user_service.common.annotation.Adaptor;
import com.caring.user_service.common.service.DatabaseCleanUp;
import com.caring.user_service.domain.authority.business.adaptor.AuthorityAdaptor;
import com.caring.user_service.domain.authority.entity.Authority;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.domain.manager.business.service.ManagerDomainService;
import com.caring.user_service.presentation.manager.vo.response.ResponseSpecificManager;
import com.netflix.discovery.converters.Auto;
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
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class GetAllManagersUseCaseTest {

    @Autowired
    GetAllManagersUseCase getAllManagersUseCase;
    @Autowired
    AuthorityDataInitializer authorityDataInitializer;
    @Autowired
    ManagerDomainService managerDomainService;
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
    @DisplayName("모든 매니저의 정보를 response 형태로 불러옵니다.")
    void getAllManagersUseCase(){
        //given
        Authority managerAuth = authorityAdaptor.queryByManagerRole(ManagerRole.MANAGE);
        Authority superAuth = authorityAdaptor.queryByManagerRole(ManagerRole.SUPER);
        managerDomainService.registerManager("name1", "password", managerAuth);
        managerDomainService.registerManager("name2", "password", superAuth);
        managerDomainService.registerManager("name3", "password", superAuth);
        //when
        List<ResponseSpecificManager> result = getAllManagersUseCase.execute();
        //then
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(2).getResponseAuthority().getRoles()).contains(ManagerRole.SUPER.getKey());
    }

}