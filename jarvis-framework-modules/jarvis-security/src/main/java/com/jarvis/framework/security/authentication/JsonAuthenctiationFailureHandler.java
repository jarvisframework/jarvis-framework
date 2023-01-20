package com.jarvis.framework.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jarvis.framework.web.rest.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JsonAuthenctiationFailureHandler implements AuthenticationFailureHandler {
    private static Logger log = LoggerFactory.getLogger(JsonAuthenctiationFailureHandler.class);
    private ObjectMapper objectMapper;

    public JsonAuthenctiationFailureHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (log.isInfoEnabled()) {
            log.info("登录失败", exception);
        }

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();

        try {
            String message = null;
            if (exception instanceof BadCredentialsException) {
                message = "用户名或者密码输入错误，请重新输入!";
            } else if (exception instanceof LockedException) {
                message = "账户被锁定，请联系管理员!";
            } else if (exception instanceof CredentialsExpiredException) {
                message = "密码过期，请联系管理员!";
            } else if (exception instanceof AccountExpiredException) {
                message = "账户过期，请联系管理员!";
            } else if (exception instanceof DisabledException) {
                message = "账户被禁用，请联系管理员!";
            } else {
                message = exception.getMessage();
            }

            message = exception.getMessage();
            writer.write(this.objectMapper.writeValueAsString(RestResponse.response(HttpStatus.UNAUTHORIZED, message)));
            writer.flush();
        } finally {
            if (null != writer) {
                writer.close();
            }

        }

    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
