package com.jarvis.framework.oauth2.resource.server.introspection;

import com.jarvis.framework.oauth2.resource.server.model.Oauth2SecurityUser;
import com.jarvis.framework.oauth2.server.token.Oauth2TokenObtainService;
import org.springframework.beans.BeanUtils;
import org.springframework.core.ResolvableType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;

public class RedisOpaqueTokenIntrospector implements OpaqueTokenIntrospector {
    private final Oauth2TokenObtainService oauth2TokenObtainService;

    public RedisOpaqueTokenIntrospector(Oauth2TokenObtainService oauth2TokenObtainService) {
        this.oauth2TokenObtainService = oauth2TokenObtainService;
    }

    public OAuth2AuthenticatedPrincipal introspect(String token) {
        Authentication authentication;

        try {
            authentication = this.oauth2TokenObtainService.getByAccessToken(token);
        } catch (Exception var6) {
            throw new OAuth2AuthenticationException(new OAuth2Error("invalid_token", "获取令牌出错", (String)null));
        }

        if (authentication == null) {
            throw new OAuth2AuthenticationException(new OAuth2Error("invalid_token", "令牌无效或过期", (String)null));
        } else {
            Object principal = authentication.getPrincipal();
            // todo 编译错误
            // Collection<GrantedAuthority> authorities = authentication.getAuthorities();
            Oauth2SecurityUser oAuth2AuthenticatedPrincipal = toOAuth2AuthenticatedPrincipal(principal);
            // oAuth2AuthenticatedPrincipal.addAuthorities(null);
            return oAuth2AuthenticatedPrincipal;
        }
    }

    private static Oauth2SecurityUser toOAuth2AuthenticatedPrincipal(Object principal) {
        Oauth2SecurityUser user = new Oauth2SecurityUser();
        HashMap attributes = new HashMap();

        try {
            PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(Oauth2SecurityUser.class);
            PropertyDescriptor[] var7 = targetPds;
            int var6 = targetPds.length;

            for(int var5 = 0; var5 < var6; ++var5) {
                PropertyDescriptor targetPd = var7[var5];
                Method writeMethod = targetPd.getWriteMethod();
                if (writeMethod != null) {
                    PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(principal.getClass(), targetPd.getName());
                    if (sourcePd != null) {
                        Method readMethod = sourcePd.getReadMethod();
                        if (readMethod != null) {
                            ResolvableType sourceResolvableType = ResolvableType.forMethodReturnType(readMethod);
                            ResolvableType targetResolvableType = ResolvableType.forMethodParameter(writeMethod, 0);
                            boolean isAssignable = !sourceResolvableType.hasUnresolvableGenerics() && !targetResolvableType.hasUnresolvableGenerics() ? targetResolvableType.isAssignableFrom(sourceResolvableType) : ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType());
                            if (isAssignable) {
                                if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                    readMethod.setAccessible(true);
                                }

                                Object value = readMethod.invoke(principal);
                                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                    writeMethod.setAccessible(true);
                                }

                                writeMethod.invoke(user, value);
                                if (value != null) {
                                    String propertyName = targetPd.getName();
                                    attributes.put(propertyName, value);
                                }
                            }
                        }
                    }
                }
            }

            user.setAttributes(attributes);
            return user;
        } catch (Exception var16) {
            throw new OAuth2AuthenticationException(new OAuth2Error("server_error", "登录信息转换出错", (String)null), var16);
        }
    }
}
