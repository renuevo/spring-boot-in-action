package com.github.renuevo.feign;

import feign.MethodMetadata;
import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import static feign.Util.checkState;

public class ReactiveSpringQueryMapProcess implements AnnotatedParameterProcessor {
    private static final Class<ReactiveSpringQueryMap> ANNOTATION = ReactiveSpringQueryMap.class;

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

        Field[] fields = parameterType.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            context.setParameterName(name);
            Collection<String> query = context.setTemplateParameter(name, data.template().queries().get(name));
            data.template().query(name, query);
        }

        return true;
    }
}
