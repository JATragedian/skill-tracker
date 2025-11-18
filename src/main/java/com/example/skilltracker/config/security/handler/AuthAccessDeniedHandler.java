package com.example.skilltracker.config.security.handler;

import com.example.skilltracker.service.util.TimeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class AuthAccessDeniedHandler implements AccessDeniedHandler {

    private final TimeService timeService;

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        response.getWriter().write("""
            {
                "error": "Forbidden",
                "timestamp": "%s"
            }
        """.formatted(timeService.nowLocalDateTime()));
    }
}
