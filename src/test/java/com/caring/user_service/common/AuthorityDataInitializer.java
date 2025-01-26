package com.caring.user_service.common;

import com.caring.user_service.domain.authority.entity.Authority;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.domain.authority.repository.AuthorityRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AuthorityDataInitializer {

    private final AuthorityRepository authorityRepository;

//    @PostConstruct
    public void initAuthorityData() {
        if (authorityRepository.count() == 0) {
            authorityRepository.save(Authority
                    .builder()
                    .managerRole(ManagerRole.MANAGE)
                    .build());
            authorityRepository.save(Authority
                    .builder()
                    .managerRole(ManagerRole.SUPER)
                    .build());

        }
    }
}
