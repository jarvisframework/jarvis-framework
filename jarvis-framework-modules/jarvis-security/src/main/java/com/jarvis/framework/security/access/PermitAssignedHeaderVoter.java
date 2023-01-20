package com.jarvis.framework.security.access;

import com.jarvis.framework.webmvc.util.WebUtil;
import java.util.Collection;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;

public class PermitAssignedHeaderVoter implements AccessDecisionVoter<Object> {
    private final String name;
    private final String value;

    public PermitAssignedHeaderVoter(String name, String value) {
        Assert.hasText(name, "argument[name] must have text; it must not be null, empty, or blank");
        Assert.hasText(value, "argument[value] must have text; it must not be null, empty, or blank");
        this.name = name;
        this.value = value;
    }

    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }

    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        String header = WebUtil.getHeader(this.name);
        return this.value.equals(header) ? 1 : 0;
    }
}
