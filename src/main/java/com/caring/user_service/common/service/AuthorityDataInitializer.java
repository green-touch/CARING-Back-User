package com.caring.user_service.common.service;

import com.caring.user_service.domain.authority.entity.Authority;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.domain.authority.repository.AuthorityRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class AuthorityDataInitializer {

    private final AuthorityRepository authorityRepository;

    @PostConstruct
    public void setup() {
        Set<ManagerRole> allInDatabase = authorityRepository.findAllManagerRoles();
        List<Authority> notInserted = Arrays.stream(ManagerRole.values())
                .filter(managerRole -> !allInDatabase.contains(managerRole))
                .map(managerRole ->
                        Authority.builder()
                                .managerRole(managerRole)
                                .build())
                .collect(Collectors.toList());
        authorityRepository.saveAll(notInserted);

    }
}
