package com.jarvis.framework.security.authentication.sso.converter;

import com.jarvis.framework.cypto.util.AESUtil;
import com.jarvis.framework.security.authentication.sso.SsoAuthenticationToken;
import com.jarvis.framework.security.authentication.sso.SsoTokenModel;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class SsoAuthenticationConverter {
    public static final String AUTHENTICATION_HEADER_TOKEN = "X-Token";
    public static final String AUTHENTICATION_SCHEME_TOKEN = "{SSO}";

    public SsoAuthenticationToken convert(HttpServletRequest request) {
        String header = request.getHeader("X-Token");
        if (header == null) {
            throw new BadCredentialsException("token不能为空");
        } else {
            header = header.trim();
            if (!StringUtils.startsWithIgnoreCase(header, "{SSO}")) {
                throw new BadCredentialsException("非法token");
            } else if (header.equalsIgnoreCase("{SSO}")) {
                throw new BadCredentialsException("token不能为空");
            } else {
                String token;
                try {
                    token = AESUtil.decrypt(header.substring(5));
                } catch (Exception var8) {
                    throw new BadCredentialsException("非法token");
                }

                int delim = token.indexOf(":");
                if (delim == -1) {
                    throw new BadCredentialsException("非法token");
                } else {
                    String[] tokens = token.split(":");
                    SsoTokenModel model = new SsoTokenModel(tokens[0], tokens[2], tokens[1], Long.parseLong(tokens[3]));
                    SsoAuthenticationToken result = new SsoAuthenticationToken(header, model);
                    return result;
                }
            }
        }
    }
}
