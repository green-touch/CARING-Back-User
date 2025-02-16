package com.caring.user_service.presentation.manager.vo.response;

import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.presentation.manager.mapper.ManagerMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Builder
@RequiredArgsConstructor
public class ResponseSpecificManager {
    private final ResponseManager responseManager;
    private final ResponseAuthority responseAuthority;

    public static ResponseSpecificManager of(Manager manager) {
        return ResponseSpecificManager.builder()
                .responseManager(ManagerMapper.INSTANCE.toResponseManager(manager))
                .responseAuthority(ResponseAuthority.of(manager))
                .build();
    }
}
