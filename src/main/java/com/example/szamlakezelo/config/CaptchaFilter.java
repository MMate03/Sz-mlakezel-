package com.example.szamlakezelo.config;

import com.example.szamlakezelo.service.LoginAttemptService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CaptchaFilter extends OncePerRequestFilter {

    private final AuthenticationFailureHandler failureHandler;
    private final LoginAttemptService loginAttemptService;

    public CaptchaFilter(AuthenticationFailureHandler failureHandler, LoginAttemptService loginAttemptService) {
        this.failureHandler = failureHandler;
        this.loginAttemptService = loginAttemptService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        if ("/login".equals(request.getRequestURI()) && "POST".equalsIgnoreCase(request.getMethod())) {
            String username = request.getParameter("username");

            // Csak ha szükséges captcha
            if (username != null && loginAttemptService.isCaptchaRequired(username)) {
                String captchaInput = request.getParameter("captcha");
                String captchaExpected = (String) request.getSession().getAttribute("captcha");

                if (captchaExpected == null || !captchaExpected.equalsIgnoreCase(captchaInput)) {
                    failureHandler.onAuthenticationFailure(
                            request,
                            response,
                            new AuthenticationException("Hibás captcha!") {}
                    );
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
