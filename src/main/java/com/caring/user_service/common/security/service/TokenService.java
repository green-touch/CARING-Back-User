package com.caring.user_service.common.security.service;

import com.caring.user_service.common.security.dto.JwtToken;
import org.springframework.security.core.Authentication;

import java.util.Date;

public interface TokenService {
    JwtToken login(String userNumber, String password);

    JwtToken issueToken(String refreshToken);

    JwtToken generateToken(Authentication authentication);

    Authentication getAuthentication(String accessToken);

    boolean logout(String refreshToken);

    boolean existsRefreshToken(String refreshToken);
}
