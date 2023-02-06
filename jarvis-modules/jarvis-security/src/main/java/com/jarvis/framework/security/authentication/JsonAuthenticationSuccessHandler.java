package com.jarvis.framework.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jarvis.framework.web.rest.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

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
public class JsonAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static Logger log = LoggerFactory.getLogger(JsonAuthenticationSuccessHandler.class);

    private final ObjectMapper objectMapper;

    public JsonAuthenticationSuccessHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.web.authentication.
     * AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.
     * HttpServletRequest, javax.servlet.http.HttpServletResponse,
     * org.springframework.security.core.Authentication)
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        if (log.isInfoEnabled()) {
            log.info("登录成功");
        }

        response.setContentType("application/json;charset=UTF-8");
        final PrintWriter writer = response.getWriter();
        try {
            writer.write(objectMapper.writeValueAsString(RestResponse.success(authentication.getPrincipal())));
            writer.flush();
        } finally {
            if (null != writer) {
                writer.close();
            }
        }

    }

}
