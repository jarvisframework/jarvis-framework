package com.jarvis.framework.security.authentication.sso.converter;

import com.jarvis.framework.cypto.util.AESUtil;
import com.jarvis.framework.security.authentication.sso.SsoAuthenticationToken;
import com.jarvis.framework.security.authentication.sso.SsoTokenModel;
import com.jarvis.framework.token.util.SsoTokenUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年3月18日
 */
public class SsoAuthenticationConverter {

    public static final String AUTHENTICATION_HEADER_TOKEN = SsoTokenUtil.HEADER;

    public static final String AUTHENTICATION_SCHEME_TOKEN = SsoTokenUtil.SCHEME;

    public SsoAuthenticationToken convert(HttpServletRequest request) {
        String header = request.getHeader(AUTHENTICATION_HEADER_TOKEN);
        if (header == null) {
            throw new BadCredentialsException("token不能为空");
        }
        header = header.trim();
        if (!StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME_TOKEN)) {
            throw new BadCredentialsException("非法token");
        }
        if (header.equalsIgnoreCase(AUTHENTICATION_SCHEME_TOKEN)) {
            throw new BadCredentialsException("token不能为空");
        }
        String token;
        try {
            token = AESUtil.decrypt(header.substring(5));
        } catch (final Exception e) {
            throw new BadCredentialsException("非法token");
        }
        final int delim = token.indexOf(SsoTokenUtil.DELIM);
        if (delim == -1) {
            throw new BadCredentialsException("非法token");
        }
        final String[] tokens = token.split(SsoTokenUtil.DELIM);
        final SsoTokenModel model = new SsoTokenModel(tokens[0], tokens[2], tokens[1], Long.parseLong(tokens[3]));
        final SsoAuthenticationToken result = new SsoAuthenticationToken(header, model);
        return result;
    }

}
