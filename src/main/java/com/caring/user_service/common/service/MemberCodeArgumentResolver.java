package com.caring.user_service.common.service;

import com.caring.user_service.common.annotation.MemberCode;
import com.caring.user_service.common.consts.StaticVariable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.caring.user_service.common.consts.StaticVariable.REQUEST_HEADER_MEMBER_CODE;


public class MemberCodeArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(MemberCode.class) != null &&
                parameter.getParameterType().equals(String.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        return request.getHeader(REQUEST_HEADER_MEMBER_CODE);
    }
}
