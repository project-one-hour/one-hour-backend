package com.project1hour.api.core.presentation.controller;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.server.PathContainer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.pattern.PathPatternParser;

public abstract class AnnotatedUrlMappingFilter<A extends Annotation> extends ExceptionHandleAdapterFilter
        implements ApplicationListener<ServletWebServerInitializedEvent> {

    private List<String> includeUrlPatterns;

    @Override
    public final void onApplicationEvent(final ServletWebServerInitializedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        this.includeUrlPatterns = extractDynamicUrlPatterns(applicationContext);
    }

    @Override
    protected boolean shouldNotFilter(final HttpServletRequest request) throws ServletException {
        PathContainer requestPath = PathContainer.parsePath(request.getRequestURI());
        boolean isMatched = includeUrlPatterns.stream()
                .anyMatch(includeUrl -> PathPatternParser.defaultInstance.parse(includeUrl).matches(requestPath));

        return BooleanUtils.isFalse(isMatched);
    }

    private List<String> extractDynamicUrlPatterns(ApplicationContext applicationContext) {
        Class<? extends Annotation> targetAnnotation = resolveGenericAnnotationClass();

        return applicationContext.getBeansWithAnnotation(Controller.class).values().stream()
                .flatMap(controller -> extractUrlPatternsFromController(controller.getClass(), targetAnnotation))
                .toList();
    }

    private Stream<String> extractUrlPatternsFromController(final Class<?> controllerClass,
                                                            final Class<? extends Annotation> annotationClass) {
        String[] classPaths =
                Optional.ofNullable(findMergedAnnotation(controllerClass, RequestMapping.class))
                        .map(RequestMapping::value)
                        .orElseGet(() -> new String[]{""});

        List<String> memberOnlyRequestUrls =
                Arrays.stream(controllerClass.getDeclaredMethods())
                        .filter(method -> AnnotatedElementUtils.hasAnnotation(method, annotationClass))
                        .filter(method -> AnnotatedElementUtils.hasAnnotation(method, RequestMapping.class))
                        .map(method -> findMergedAnnotation(method, RequestMapping.class).value())
                        .flatMap(Arrays::stream)
                        .toList();

        return memberOnlyRequestUrls.stream()
                .flatMap(methodPath -> Arrays.stream(classPaths).map(classPath -> classPath + methodPath));
    }

    /**
     * 해당 접근 방식은 런타임 시 안정성 이슈가 발생할 수 있습니다. 서브클래스에서 직접 어노테이션 클래스를 반환하는 방식으로 대체하는 것이 더 안전합니다.
     */
    @SuppressWarnings("unchecked")
    protected Class<A> resolveGenericAnnotationClass() {
        String annotationTypeName = "";

        if (getClass().getGenericSuperclass() instanceof ParameterizedType type) {
            annotationTypeName = type.getActualTypeArguments()[0].getTypeName();
        }

        try {
            return (Class<A>) ClassUtils.getClass(annotationTypeName);
        } catch (Exception e) {
            throw new IllegalArgumentException("유효하지 않은 어노테이션입니다.", e.getCause());
        }
    }
}
