package com.github.renuevo.feign.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @see CustomParamAnnotationProcess
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomParam {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

}
