package com.caring.user_service.domain.shelter.business.service;

import com.caring.user_service.common.service.DatabaseCleanUp;
import com.caring.user_service.domain.shelter.business.adaptor.ShelterAdaptor;
import com.caring.user_service.domain.shelter.entity.Shelter;
import com.caring.user_service.domain.shelter.repository.ShelterRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class ShelterDomainServiceTest {

    @Autowired
    ShelterDomainService shelterDomainService;
    @Autowired
    ShelterRepository shelterRepository;
    @Autowired
    DatabaseCleanUp databaseCleanUp;

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }
    @Test
    @DisplayName("보호소의 이름과 위치를 작성하여 shelter 데이터를 입력합니다.")
    void registerShelter(){
        //given
        String name = "shelter";
        String location = "location";
        //when
        Long shelterId = shelterDomainService.registerShelter(name, location);
        //then
        Optional<Shelter> findShelter = shelterRepository.findById(shelterId);
        assertThat(findShelter).isPresent();
        assertThat(findShelter.get().getName()).isEqualTo(name);
    }

}