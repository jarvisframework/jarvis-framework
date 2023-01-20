package com.jarvis.framework.autoconfigure.security;

import com.jarvis.framework.security.authentication.config.BadCreadentialsProperties;
import com.jarvis.framework.security.validation.code.config.ValidateCodeProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(
    prefix = "spring.security"
)
public class ArchiveSecurityProperties {
    private ValidateCodeProperties validateCode = new ValidateCodeProperties();
    private SsoTokenProperties ssoToken = new SsoTokenProperties();
    private final List<String> denyUrls = new ArrayList();
    private final List<String> ignoreUrls = new ArrayList();
    private final List<String> permitUrls = new ArrayList();
    private BadCreadentialsProperties badCreadentials = new BadCreadentialsProperties();
    private String passwordEncoder = "MD5";
    private final List<AuthorityUrl> authorityUrls = new ArrayList();
    private String permitAccessId;

    public BadCreadentialsProperties getBadCreadentials() {
        return a.badCreadentials;
    }

    public String getPermitAccessId() {
        return a.permitAccessId;
    }

    public void setSsoToken(SsoTokenProperties a) {
        a.ssoToken = a;
    }

    public String getPasswordEncoder() {
        return a.passwordEncoder;
    }

    public void setValidateCode(ValidateCodeProperties a) {
        a.validateCode = a;
    }

    public ValidateCodeProperties getValidateCode() {
        return a.validateCode;
    }

    public List<String> getIgnoreUrls() {
        return a.ignoreUrls;
    }

    public List<AuthorityUrl> getAuthorityUrls() {
        return a.authorityUrls;
    }

    public void setPasswordEncoder(String a) {
        a.passwordEncoder = a;
    }

    public List<String> getPermitUrls() {
        return a.permitUrls;
    }

    public SsoTokenProperties getSsoToken() {
        return a.ssoToken;
    }

    public void setBadCreadentials(BadCreadentialsProperties a) {
        a.badCreadentials = a;
    }

    public ArchiveSecurityProperties() {
    }

    public List<String> getDenyUrls() {
        return a.denyUrls;
    }

    public void setPermitAccessId(String a) {
        a.permitAccessId = a;
    }
}
