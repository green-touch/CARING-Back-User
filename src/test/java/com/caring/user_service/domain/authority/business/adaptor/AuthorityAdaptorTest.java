package com.caring.user_service.domain.authority.business.adaptor;

import com.caring.user_service.common.AuthorityDataInitializer;
import com.caring.user_service.domain.authority.entity.Authority;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.domain.authority.repository.AuthorityRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class AuthorityAdaptorTest {

    @Autowired
    AuthorityRepository authorityRepository;
    @Autowired
    AuthorityAdaptor authorityAdaptor;
    @Autowired
    AuthorityDataInitializer authorityDataInitializer;

    @BeforeEach
    void setup() {
        authorityDataInitializer.initAuthorityData();;
    }
    @Test
    @DisplayName("ManagerRole enum의 키값을 통해 authority entity를 불러옵니다.")
    void queryByManagerRole(){
        //given

        //when
        Authority managerAuthority = authorityAdaptor.queryByManagerRole(ManagerRole.MANAGE);
        Authority superAuthority = authorityAdaptor.queryByManagerRole(ManagerRole.SUPER);
        //then
        assertThat(managerAuthority).isNotNull();
        assertThat(superAuthority).isNotNull();
        assertThat(managerAuthority.getManagerRole()).isEqualTo(ManagerRole.MANAGE);
        assertThat(superAuthority.getManagerRole()).isEqualTo(ManagerRole.SUPER);

    }

}