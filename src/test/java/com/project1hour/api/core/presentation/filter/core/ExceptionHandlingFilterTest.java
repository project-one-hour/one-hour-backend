package com.project1hour.api.core.presentation.filter.core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project1hour.api.global.advice.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@SpringBootTest
@ContextConfiguration(classes = ApplicationContext.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ExceptionHandlingFilterTest {

    private TestExceptionHandlingFilter filter;

    private HandlerExceptionResolver resolver;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        filter = new TestExceptionHandlingFilter();
        resolver = mock(HandlerExceptionResolver.class);
        objectMapper = mock(ObjectMapper.class);
        ApplicationContext applicationContext = mock(ApplicationContext.class);

        when(applicationContext.getBean("handlerExceptionResolver", HandlerExceptionResolver.class))
                .thenReturn(resolver);
        when(applicationContext.getBean(ObjectMapper.class))
                .thenReturn(objectMapper);

        filter.setApplicationContext(applicationContext);
    }

    @Test
    void 예외가_발생할_때_해당_예외_처리기가_없다면_500_서버_에러를_응답한다() throws Exception {
        // given
        String errorResponseJson = "{\"errorCode\":\"INTERNAL_SERVER_ERROR\",\"errorMessage\":\"예외 발생~\"}";
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        // when
        when(response.getWriter()).thenReturn(mock(PrintWriter.class));
        when(resolver.resolveException(any(), any(), any(), any())).thenReturn(null);
        when(objectMapper.writeValueAsString(any(ErrorResponse.class))).thenReturn(errorResponseJson);
        filter.doFilterInternal(request, response, chain);

        // then
        verify(response).setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        verify(response).setContentType(MediaType.APPLICATION_JSON_VALUE);
        verify(response).setCharacterEncoding(StandardCharsets.UTF_8.name());
        verify(response.getWriter()).write(errorResponseJson);
    }

    @Test
    void 예외가_발생할_때_해당_예외_처리기가_있다면_예외를_반환하지_않는다() throws Exception {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        // when
        when(resolver.resolveException(any(), any(), any(), any())).thenReturn(new ModelAndView());

        // then
        assertDoesNotThrow(() -> filter.doFilterInternal(request, response, chain));
    }

    static class TestExceptionHandlingFilter extends ExceptionHandlingFilter {

        @Override
        protected void doProcessFilter(final HttpServletRequest request, final HttpServletResponse response,
                                       final FilterChain filterChain) throws ServletException, IOException {
            throw new RuntimeException("예외 발생~");
        }
    }
}
