package com.jarvis.framework.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jarvis.framework.security.exception.GetParameterAuthentionException;
import com.jarvis.framework.web.rest.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月28日
 */
public class JsonAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    /**
     * @param objectMapper
     */
    public JsonAuthenticationEntryPoint(ObjectMapper objectMapper) {
        super();
        this.objectMapper = objectMapper;
    }

    /**
     *
     * @see org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint#commence(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        response.setContentType("application/json;charset=UTF-8");
        final PrintWriter writer = response.getWriter();
        try {
            if (authException instanceof GetParameterAuthentionException) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                writer.write(objectMapper
                        .writeValueAsString(RestResponse.response(HttpStatus.BAD_REQUEST, authException.getMessage())));
            } else {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                writer.write(objectMapper
                        .writeValueAsString(RestResponse.response(HttpStatus.UNAUTHORIZED, authException.getMessage())));
            }
            writer.flush();
        } finally {
            if (null != writer) {
                writer.close();
            }
        }

    }

}

