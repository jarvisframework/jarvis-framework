package com.jarvis.framework.oauth2.resource.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jarvis.framework.security.model.SecurityUser;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.util.Map;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月25日
 */
public class Oauth2SecurityUser extends SecurityUser implements OAuth2AuthenticatedPrincipal {

    private Map<String, Object> attributes;

    /**
     *
     */
    private static final long serialVersionUID = -6675058699954432546L;

    /**
     *
     * @see org.springframework.security.core.AuthenticatedPrincipal#getName()
     */
    @Override
    public String getName() {
        return getUsername();
    }

    /**
     *
     * @see org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal#getAttributes()
     */
    @Override
    @JsonIgnore
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

}
