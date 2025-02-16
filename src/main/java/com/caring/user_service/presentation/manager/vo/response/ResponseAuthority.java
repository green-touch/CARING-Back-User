package com.caring.user_service.presentation.manager.vo.response;

import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.domain.manager.entity.Manager;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class ResponseAuthority {

    @Builder.Default
    private final List<String> roles = new ArrayList<>();

    public static ResponseAuthority of(Manager manager) {
        return ResponseAuthority.builder()
                .roles(manager.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList())
                .build();
    }
}
