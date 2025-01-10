package com.caring.user_service.common.security.service;

import com.caring.user_service.common.security.dto.JwtToken;
import com.caring.user_service.common.service.RedisService;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Slf4j
@Service
public class TokenServiceImpl implements TokenService{

    private final Key key;      //security yml 파일 생성 후 app.jwt.secret에 값 넣어주기(보안을 위해 따로 연락주세요)
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final Environment env;
    private final RedisService redisService;

    public TokenServiceImpl(AuthenticationManagerBuilder authenticationManagerBuilder,
                            Environment environment,
                            RedisService redisService) {
        byte[] keyBytes = Decoders.BASE64.decode(environment.getProperty("token.secret"));
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.env = environment;
        this.redisService = redisService;
//        this.memberQueryService = memberQueryService;
    }
    @Override
    public JwtToken login(String kakaoEmail) {
        return null;
    }

    @Override
    public JwtToken issueTokens(String refreshToken) {
        return null;
    }

    @Override
    public JwtToken generateToken(Authentication authentication) {
        return null;
    }

    @Override
    public Authentication getAuthentication(String accessToken) {
        return null;
    }

    @Override
    public boolean validateToken(String token) {
        return false;
    }

    @Override
    public boolean logout(String refreshToken) {
        return false;
    }

    @Override
    public boolean existsRefreshToken(String refreshToken) {
        return false;
    }

    @Override
    public Date parseExpiration(String token) {
        return null;
    }
}
