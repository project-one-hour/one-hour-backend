package com.project1hour.api.core.presentation.filter;

import com.project1hour.api.core.presentation.filter.core.ExceptionHandlingFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class FaviconIgnoreFilter extends ExceptionHandlingFilter {

    private static final String FAVICON_REQUEST_URI = "/favicon.ico";

    @Override
    protected void doProcessFilter(final HttpServletRequest request, final HttpServletResponse response,
                                   final FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals(FAVICON_REQUEST_URI)) {
            return;
        }
        filterChain.doFilter(request, response);
    }
}
