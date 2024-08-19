package com.jarvis.framework.autoconfigure.security;

import com.jarvis.framework.security.authentication.config.BadCreadentialsProperties;
import com.jarvis.framework.security.validation.code.config.ValidateCodeProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年2月3日
 */
@ConfigurationProperties(prefix = "spring.security")
public class JarvisSecurityProperties {

    /**
     * 放行的url
     */
    private final List<String> permitUrls = new ArrayList<String>();

    /**
     * 禁止访问的url
     */
    private final List<String> denyUrls = new ArrayList<String>();

    /**
     * 需要权限的url
     */
    private final List<AuthorityUrl> authorityUrls = new ArrayList<>();

    /**
     * 忽略的URL
     */
    private final List<String> ignoreUrls = new ArrayList<>();

    /**
     * 验证码
     */
    private ValidateCodeProperties validateCode = new ValidateCodeProperties();

    /**
     * 登录密码连续输错配置
     */
    private BadCreadentialsProperties badCreadentials = new BadCreadentialsProperties();

    /**
     * SSO单点登录
     */
    private SsoTokenProperties ssoToken = new SsoTokenProperties();

    /**
     * 允许无登录访问的ID（feign接口使用）
     */
    private String permitAccessId;

    /**
     * 密码加密方式：bcrypt/scrypt/SM3/MD5/MD4/ldap/noop/pbkdf2/SHA-1/SHA-256/sha256/argon2
     * 默认是MD5
     */
    private String passwordEncoder = "MD5";

    /**
     * @return the permitUrls
     */
    public List<String> getPermitUrls() {
        return permitUrls;
    }

    /**
     * @return the denyUrls
     */
    public List<String> getDenyUrls() {
        return denyUrls;
    }

    /**
     * @return the authorityUrls
     */
    public List<AuthorityUrl> getAuthorityUrls() {
        return authorityUrls;
    }

    /**
     * @return the ignoreUrls
     */
    public List<String> getIgnoreUrls() {
        return ignoreUrls;
    }

    /**
     * @return the validateCode
     */
    public ValidateCodeProperties getValidateCode() {
        return validateCode;
    }

    /**
     * @param validateCode the validateCode to set
     */
    public void setValidateCode(ValidateCodeProperties validateCode) {
        this.validateCode = validateCode;
    }

    /**
     * @return the badCreadentials
     */
    public BadCreadentialsProperties getBadCreadentials() {
        return badCreadentials;
    }

    /**
     * @param badCreadentials the badCreadentials to set
     */
    public void setBadCreadentials(BadCreadentialsProperties badCreadentials) {
        this.badCreadentials = badCreadentials;
    }

    /**
     * @return the ssoToken
     */
    public SsoTokenProperties getSsoToken() {
        return ssoToken;
    }

    /**
     * @param ssoToken the ssoToken to set
     */
    public void setSsoToken(SsoTokenProperties ssoToken) {
        this.ssoToken = ssoToken;
    }

    /**
     * @return the permitAccessId
     */
    public String getPermitAccessId() {
        return permitAccessId;
    }

    /**
     * @param permitAccessId the permitAccessId to set
     */
    public void setPermitAccessId(String permitAccessId) {
        this.permitAccessId = permitAccessId;
    }

    /**
     * @return the passwordEncoder
     */
    public String getPasswordEncoder() {
        return passwordEncoder;
    }

    /**
     * @param passwordEncoder the passwordEncoder to set
     */
    public void setPasswordEncoder(String passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

}
