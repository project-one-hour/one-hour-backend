package com.project1hour.api.core.presentation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project1hour.api.global.advice.ErrorCode;
import com.project1hour.api.global.advice.ErrorResponse;
import io.jsonwebtoken.lang.Assert;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.NonNull;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public abstract class ExceptionHandleAdapterFilter extends OncePerRequestFilter implements ApplicationContextAware {

    /**
     * WebMvcEndpointChildContextConfiguration 클래스 참고 / (Bean Name)
     */
    private final static String HANDLER_EXCEPTION_RESOLVER_BEAN_NAME = "handlerExceptionResolver";

    private ApplicationContext applicationContext;

    @Override
    public final void setApplicationContext(@NonNull final ApplicationContext applicationContext)
            throws BeansException {
        Assert.isNull(this.applicationContext, "ApplicationContext가 이미 설정되었습니다.");
        this.applicationContext = applicationContext;
    }

    @Override
    protected final void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                          final FilterChain filterChain) throws ServletException, IOException {
        try {
            doProcessFilter(request, response, filterChain);
        } catch (Exception exception) {
            HandlerExceptionResolver exceptionResolver =
                    applicationContext.getBean(HANDLER_EXCEPTION_RESOLVER_BEAN_NAME, HandlerExceptionResolver.class);
            ModelAndView result = exceptionResolver.resolveException(request, response, null, exception);

            if (ObjectUtils.isEmpty(result)) {
                setErrorResponse(response, exception);
            }
        }
    }

    private void setErrorResponse(HttpServletResponse response, Exception exception) throws IOException {
        String errorMessage = exception.getMessage();
        String errorCode = ErrorCode.INTERNAL_SERVER_ERROR.getErrorCode();

        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(errorResponseBody(errorMessage, errorCode));
    }


    private String errorResponseBody(String errorMessage, String errorCode) throws JsonProcessingException {
        ObjectMapper objectMapper = applicationContext.getBean(ObjectMapper.class);
        ErrorResponse errorResponse = ErrorResponse.of(errorCode, errorMessage);
        return objectMapper.writeValueAsString(errorResponse);
    }

    protected abstract void doProcessFilter(final HttpServletRequest request, final HttpServletResponse response,
                                            final FilterChain filterChain) throws ServletException, IOException;

}
