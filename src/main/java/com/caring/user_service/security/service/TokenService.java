package com.caring.user_service.security.service;

import com.caring.user_service.security.dto.JwtToken;
import org.springframework.security.core.Authentication;

public interface TokenService {
    JwtToken login(String userNumber, String password);

    JwtToken reissueToken(String refreshToken);

    JwtToken generateToken(Authentication authentication);

    Authentication getAuthentication(String accessToken);

    boolean logout(String refreshToken);

    boolean existsRefreshToken(String refreshToken);
}
