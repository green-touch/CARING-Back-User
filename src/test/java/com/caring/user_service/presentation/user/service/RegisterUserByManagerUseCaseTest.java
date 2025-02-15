package com.caring.user_service.presentation.user.service;

import com.caring.user_service.common.service.DatabaseCleanUp;
import com.caring.user_service.domain.shelter.business.service.ShelterDomainService;
import com.caring.user_service.domain.shelter.entity.Shelter;
import com.caring.user_service.domain.user.business.domainservice.UserDomainService;
import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.domain.user.repository.UserRepository;
import com.caring.user_service.presentation.user.vo.RequestUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
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
class RegisterUserByManagerUseCaseTest {
    
    @Autowired
    RegisterUserByManagerUseCase registerUserByManagerUseCase;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ShelterDomainService shelterDomainService;
    @Autowired
    DatabaseCleanUp databaseCleanUp;
    
    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }
    
    @Test
    @Transactional
    @DisplayName("매니저에 의해 유저 계정이 생성됩니다. 이때 인가(Authorization)는 필터에서 확인하며, 해당 메서드에서는 " +
            "유저 생성, 보호소 소속 설정과 같은 과정을 거치게 됩니다.")
    void registerUserByManagerUseCase(){
        //given
        Shelter shelter = shelterDomainService.registerShelter("shelter", "location");
        RequestUser request = RequestUser.builder()
                .name("name")
                .password("password")
                .build();
        //when
        Long userId = registerUserByManagerUseCase.execute(request, shelter.getShelterUuid());
        //then
        Optional<User> user = userRepository.findById(userId);
        assertThat(user).isPresent();
        assertThat(user.get().getName()).isEqualTo(request.getName());
        assertThat(user.get().getShelterUuid()).isEqualTo(shelter.getShelterUuid());
    }

}