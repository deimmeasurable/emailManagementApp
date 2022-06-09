package com.example.emailManagementApp.security;

import com.example.emailManagementApp.exceptions.APIError;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionHandlerFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(request, response);
        } catch (JwtException exception) {
            exception.printStackTrace();
            setErrorResponse(HttpStatus.BAD_REQUEST, response, exception);
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, exception);
        }

    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable exception) {
        response.setStatus(status.value());
        response.setContentType("application/json");
        APIError apiError = new APIError(status, exception);
        try {
            String JsonOutput = apiError.convertToJson();
            response.getWriter().write(JsonOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
