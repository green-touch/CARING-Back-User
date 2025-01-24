package com.caring.user_service.domain.authority.business.adaptor;

import com.caring.user_service.common.service.DatabaseCleanUp;
import com.caring.user_service.domain.authority.entity.Authority;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.domain.authority.repository.AuthorityRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class AuthorityAdaptorTest {

    @Autowired
    AuthorityRepository authorityRepository;
    @Autowired
    AuthorityAdaptor authorityAdaptor;

    @Test
    @DisplayName("ManagerRole enum의 키값을 통해 authority entity를 불러옵니다.")
    void queryByManagerRoleKey(){
        //given
        String manageKey = ManagerRole.MANAGE.getKey();
        String superKey = ManagerRole.SUPER.getKey();
        //when
        Authority managerAuthority = authorityAdaptor.queryByManagerRoleKey(manageKey);
        Authority superAuthority = authorityAdaptor.queryByManagerRoleKey(superKey);
        //then
        assertThat(managerAuthority).isNotNull();
        assertThat(superAuthority).isNotNull();
        assertThat(managerAuthority.getManagerRole()).isEqualTo(ManagerRole.MANAGE);
        assertThat(superAuthority.getManagerRole()).isEqualTo(ManagerRole.SUPER);

    }

}