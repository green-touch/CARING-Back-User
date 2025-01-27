package com.caring.user_service.common.config;


import com.caring.user_service.common.service.MemberCodeArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new MemberCodeArgumentResolver());
    }

//    //CORS setting
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry
//                .addMapping("/**") //CORS 적용할 URL 패턴
//                .allowedOriginPatterns("*") //자원 공유 오리진 지정
//                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") //요청 허용 메서드
//                .allowedHeaders("*") //요청 허용 헤더
//                .allowCredentials(true) //요청 허용 쿠키
//                .maxAge(MAX_AGE_SECS);
//    }
}
