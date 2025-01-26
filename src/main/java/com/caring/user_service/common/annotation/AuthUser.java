package com.caring.user_service.common.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
//@Parameter(hidden = true)
@Documented
public @interface AuthUser {

    boolean errorOnInvalidType() default true;
}
