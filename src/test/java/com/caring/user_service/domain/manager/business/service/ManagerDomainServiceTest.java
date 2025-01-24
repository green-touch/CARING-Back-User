package com.caring.user_service.domain.manager.business.service;

import com.caring.user_service.common.service.DatabaseCleanUp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class ManagerDomainServiceTest {

    @Autowired
    ManagerDomainService managerDomainService;
    @Autowired
    DatabaseCleanUp databaseCleanUp;

}