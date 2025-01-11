package com.caring.user_service.common.security.service;

import com.caring.user_service.common.security.dto.JwtToken;
import com.caring.user_service.common.service.RedisService;
import com.caring.user_service.domain.user.business.adaptor.UserAdaptor;
import com.caring.user_service.domain.user.dao.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserTokenServiceImpl implements UserTokenService {

    private final Key key;      //security yml 파일 생성 후 app.jwt.secret에 값 넣어주기(보안을 위해 따로 연락주세요)
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final Environment env;
    private final RedisService redisService;
    private final UserAdaptor userAdaptor;

    public UserTokenServiceImpl(AuthenticationManagerBuilder authenticationManagerBuilder,
                                PasswordEncoder passwordEncoder,
                                Environment environment,
                                RedisService redisService,
                                UserAdaptor userAdaptor) {
        byte[] keyBytes = Decoders.BASE64.decode(environment.getProperty("token.secret"));
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.env = environment;
        this.passwordEncoder = passwordEncoder;
        this.redisService = redisService;
        this.userAdaptor = userAdaptor;
    }
    @Override
    public JwtToken login(String userNumber, String password) {
        User user = userLoginUseCase.execute(userNumber, password);
        Authentication authentication;
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("wrong arguments(security login)");
        }
        authentication = new UsernamePasswordAuthenticationToken(user, "",
                user.getAuthorities());

        JwtToken jwtToken = generateToken(authentication);
        return jwtToken;
    }

    @Override
    public JwtToken issueToken(String refreshToken) {
        // Refresh Token 유효성 검사 -> gateway에서

        // 이전 리프레시 토큰 삭제
        redisService.deleteValue(refreshToken);

        // 새로운 Authentication 객체 생성
        Claims claims = parseClaims(refreshToken);
        String userNumber = claims.getSubject();
        User user = userAdaptor.getUserByUserNumber(userNumber);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, "",
                user.getAuthorities());

        // 새 토큰 생성
        JwtToken newTokens = generateToken(authentication);

        return newTokens;
    }

    @Override
    public JwtToken generateToken(Authentication authentication) {
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + 1800000);   // 30분
        log.info("date = {}", accessTokenExpiresIn);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName())
                .setExpiration(new Date(now + 604800000))    // 7일
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // 새 리프레시 토큰을 Redis에 저장
        redisService.setValue(refreshToken, authentication.getName());

        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public Authentication getAuthentication(String accessToken) {
        // Jwt 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new IllegalArgumentException("auth is null");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication return
        // UserDetails: interface, User: UserDetails를 구현한 class
        UserDetails principal = new org.springframework.security.core.userdetails.User
                (claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }


    @Override
    public boolean logout(String refreshToken) {
        redisService.deleteValue(refreshToken);
        return true;
    }

    @Override
    public boolean existsRefreshToken(String refreshToken) {
        return redisService.getValue(refreshToken) != null;
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
