package com.example.emailManagementApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.security.sasl.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

import static org.apache.coyote.Response.
@Component
@Slf4j
public class UnAuthorizedEntryPoint implements AuthenticationEntryPoint, Serializable {
    @Override
//    @Bean
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        log.info("unauthorized entry point ex ------->", authException);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());

    }
}
