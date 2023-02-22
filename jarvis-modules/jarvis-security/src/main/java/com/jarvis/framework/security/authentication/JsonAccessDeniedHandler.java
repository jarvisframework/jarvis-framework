package com.jarvis.framework.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jarvis.framework.web.rest.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年2月1日
 */
public class JsonAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public JsonAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     *
     * @see org.springframework.security.web.access.AccessDeniedHandler#handle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
     *      org.springframework.security.access.AccessDeniedException)
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        final PrintWriter writer = response.getWriter();
        try {
            writer.write(objectMapper.writeValueAsString(RestResponse.response(HttpStatus.FORBIDDEN, "无权访问")));
            writer.flush();
        } finally {
            if (null != writer) {
                writer.close();
            }
        }
    }

}
