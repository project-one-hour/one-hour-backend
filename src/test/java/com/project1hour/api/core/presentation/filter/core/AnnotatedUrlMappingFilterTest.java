package com.project1hour.api.core.presentation.filter.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AnnotatedUrlMappingFilterTest {

    private TestAnnotatedUrlMappingFilter filter;

    @BeforeEach
    void setup() {
        filter = new TestAnnotatedUrlMappingFilter();
        ServletWebServerInitializedEvent event = mock(ServletWebServerInitializedEvent.class);
        ServletWebServerApplicationContext applicationContext = mock(ServletWebServerApplicationContext.class);

        when(event.getApplicationContext()).thenReturn(applicationContext);
        when(applicationContext.getBeansWithAnnotation(Controller.class))
                .thenReturn(Map.of("testController", new TestController()));

        filter.onApplicationEvent(event);
    }

    @Test
    void 어노테이션이_포함된_URL이면_필터가_적용된다() throws ServletException {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/test/member");

        // when
        boolean shouldNotFilter = filter.shouldNotFilter(request);

        // then
        assertThat(shouldNotFilter).isFalse();
    }

    @Test
    void 어노테이션이_포함되지_않은_URL이면_필터가_적용되지_않는다() throws ServletException {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/test/nonMember");

        // when
        boolean shouldNotFilter = filter.shouldNotFilter(request);

        // then
        assertThat(shouldNotFilter).isTrue();
    }

    static class TestAnnotatedUrlMappingFilter extends AnnotatedUrlMappingFilter<TestAnnotation> {

        @Override
        protected void doProcessFilter(final HttpServletRequest request, final HttpServletResponse response,
                                       final FilterChain filterChain) throws ServletException, IOException {
            filterChain.doFilter(request, response);
        }
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface TestAnnotation {
    }

    @RestController
    @RequestMapping("/test")
    static class TestController {

        @TestAnnotation
        @RequestMapping("/member")
        public String memberOnlyEndpoint() {
            return "member";
        }

        @RequestMapping("/nonMember")
        public String nonMemberEndpoint() {
            return "nonMember";
        }
    }
}
