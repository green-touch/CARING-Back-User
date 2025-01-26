package com.caring.user_service.presentation.security.resolver;

import com.caring.user_service.common.annotation.AuthManager;
import com.caring.user_service.common.annotation.AuthUser;
import com.caring.user_service.common.interfaces.QueryFunction;
import com.caring.user_service.domain.manager.business.adaptor.ManagerAdaptor;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.user.business.adaptor.UserAdaptor;
import com.caring.user_service.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.annotation.Annotation;

@RequiredArgsConstructor
public class CustomAuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final ManagerAdaptor managerAdaptor;
    private final UserAdaptor userAdaptor;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return hasAnnotation(parameter, AuthManager.class) || hasAnnotation(parameter, AuthUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        // Anonymous User 처리
        if ("anonymousUser".equals(principal)) {
            throw new RuntimeException("anonymous manager or user");
        }

        // 어노테이션 확인 및 처리
        if (hasAnnotation(parameter, AuthUser.class)) {
            return resolve(parameter, authentication, userAdaptor::queryUserByMemberCode, AuthUser.class);
        } else if (hasAnnotation(parameter, AuthManager.class)) {
            return resolve(parameter, authentication, managerAdaptor::queryByMemberCode, AuthManager.class);
        }

        throw new IllegalArgumentException("Unsupported annotation found on parameter: " + parameter.getParameterName());
    }

    private <T> Object resolve(MethodParameter parameter, Authentication authentication,
                               QueryFunction<String, T> queryFunction, Class<? extends Annotation> annotationClass) {
        T entity = queryFunction.query(authentication.getName());

        if (!ClassUtils.isAssignable(parameter.getParameterType(), entity.getClass())) {
            Annotation annotation = findAnnotation(parameter, annotationClass);
            if (annotation instanceof AuthUser && ((AuthUser) annotation).errorOnInvalidType()
                    || annotation instanceof AuthManager && ((AuthManager) annotation).errorOnInvalidType()) {
                throw new RuntimeException("assignable parameter");
            }
        }

        return entity;
    }

    private boolean hasAnnotation(MethodParameter parameter, Class<? extends Annotation> annotationClass) {
        return findAnnotation(parameter, annotationClass) != null;
    }

    private <T extends Annotation> T findAnnotation(MethodParameter parameter, Class<T> annotationClass) {
        T annotation = parameter.getParameterAnnotation(annotationClass);
        if (annotation != null) {
            return annotation;
        }
        for (Annotation toSearch : parameter.getParameterAnnotations()) {
            annotation = AnnotationUtils.findAnnotation(toSearch.annotationType(), annotationClass);
            if (annotation != null) {
                return annotation;
            }
        }
        return null;
    }

}
