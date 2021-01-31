package com.github.renuevo.feign.annotation;

import feign.MethodMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import static feign.Util.checkState;

@Slf4j
public class CustomParamAnnotationProcess implements AnnotatedParameterProcessor {
    private static final Class<CustomParam> ANNOTATION = CustomParam.class;

    @Override
    public Class<? extends Annotation> getAnnotationType() {
        return ANNOTATION;
    }

    @Override
    public boolean processArgument(AnnotatedParameterContext context, Annotation annotation, Method method) {
        int parameterIndex = context.getParameterIndex();
        Class<?> parameterType = method.getParameterTypes()[parameterIndex];
        MethodMetadata data = context.getMethodMetadata();

        if (Map.class.isAssignableFrom(parameterType)) {
            checkState(data.queryMapIndex() == null, "Query map can only be present once.");
            data.queryMapIndex(parameterIndex);
            return true;
        }

        CustomParam customParam = ANNOTATION.cast(annotation);
        String name = customParam.value();
        context.setParameterName(name);
        Collection<String> query = context.setTemplateParameter(name, data.template().queries().get(name));
        data.template().query(name, query);

        /**
         * class type not execute in feign client
         * data.indexToExpanderClass().put(parameterIndex, ToJsonExpander.class);
         *
         * indexToExpander not working in reactive feign client
         * result data format is name={key1: value1, key2: value2}
         */
        data.indexToExpander().put(parameterIndex, new ToJsonExpander());

        return true;
    }
}
