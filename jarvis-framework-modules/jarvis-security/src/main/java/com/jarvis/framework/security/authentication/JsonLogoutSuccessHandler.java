package com.jarvis.framework.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jarvis.framework.web.rest.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JsonLogoutSuccessHandler implements LogoutSuccessHandler {
    private static Logger log = LoggerFactory.getLogger(JsonLogoutSuccessHandler.class);
    private final ObjectMapper objectMapper;

    public JsonLogoutSuccessHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (log.isInfoEnabled()) {
            log.info("退出成功");
        }

        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();

        try {
            writer.write(this.objectMapper.writeValueAsString(RestResponse.success("退出成功")));
            writer.flush();
        } finally {
            if (null != writer) {
                writer.close();
            }

        }

    }
}
