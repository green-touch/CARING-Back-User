package com.caring.user_service.common.security.service;

import com.caring.user_service.common.security.dto.JwtToken;
import org.springframework.security.core.Authentication;

import java.util.Date;

public interface TokenService {

    JwtToken loginUser(String userNumber, String password);
    JwtToken issueTokens(String refreshToken);

    JwtToken generateToken(Authentication authentication);

    Authentication getAuthentication(String accessToken);

    boolean validateToken(String token);

    boolean logout(String refreshToken);

    boolean existsRefreshToken(String refreshToken);

    Date parseExpiration(String token);
}
