package com.jarvis.framework.security.access;

import com.jarvis.framework.webmvc.util.WebUtil;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年5月14日
 */
public class PermitAssignedHeaderVoter implements AccessDecisionVoter<Object> {

    private final String name;

    private final String value;

    /**
     * @param name
     * @param value
     */
    public PermitAssignedHeaderVoter(String name, String value) {
        super();
        Assert.hasText(name, "argument[name] must have text; it must not be null, empty, or blank");
        Assert.hasText(value, "argument[value] must have text; it must not be null, empty, or blank");
        this.name = name;
        this.value = value;
    }

    /**
     *
     * @see org.springframework.security.access.AccessDecisionVoter#supports(org.springframework.security.access.ConfigAttribute)
     */
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    /**
     *
     * @see org.springframework.security.access.AccessDecisionVoter#supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    /**
     *
     * @see org.springframework.security.access.AccessDecisionVoter#vote(org.springframework.security.core.Authentication, java.lang.Object,
     *      java.util.Collection)
     */
    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        final String header = WebUtil.getHeader(name);
        if (value.equals(header)) {
            return ACCESS_GRANTED;
        }
        return ACCESS_ABSTAIN;
    }

}
