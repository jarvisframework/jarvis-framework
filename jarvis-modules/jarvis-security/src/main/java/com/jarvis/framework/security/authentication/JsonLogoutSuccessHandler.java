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

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月28日
 */
public class JsonLogoutSuccessHandler implements LogoutSuccessHandler {

    private static Logger log = LoggerFactory.getLogger(JsonLogoutSuccessHandler.class);

    private final ObjectMapper objectMapper;

    /**
     * @param objectMapper
     * @param responseType
     */
    public JsonLogoutSuccessHandler(ObjectMapper objectMapper) {
        super();
        this.objectMapper = objectMapper;
    }

    /**
     *
     * @see org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler#onLogoutSuccess(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        if (log.isInfoEnabled()) {
            log.info("退出成功");
        }
        response.setContentType("application/json;charset=utf-8");
        final PrintWriter writer = response.getWriter();
        try {
            writer.write(objectMapper.writeValueAsString(RestResponse.success("退出成功")));
            writer.flush();
        } finally {
            if (null != writer) {
                writer.close();
            }
        }
    }

}
