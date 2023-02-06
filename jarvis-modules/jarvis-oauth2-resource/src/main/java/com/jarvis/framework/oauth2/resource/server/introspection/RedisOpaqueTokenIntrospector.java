package com.jarvis.framework.oauth2.resource.server.introspection;

import com.jarvis.framework.oauth2.resource.server.model.Oauth2SecurityUser;
import com.jarvis.framework.oauth2.server.token.Oauth2TokenObtainService;
import org.springframework.beans.BeanUtils;
import org.springframework.core.ResolvableType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月20日
 */
public class RedisOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

    private final Oauth2TokenObtainService oauth2TokenObtainService;

    public RedisOpaqueTokenIntrospector(Oauth2TokenObtainService oauth2TokenObtainService) {
        this.oauth2TokenObtainService = oauth2TokenObtainService;
    }

    /**
     *
     * @see org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector#introspect(java.lang.String)
     */
    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        Authentication authentication = null;

        try {
            authentication = oauth2TokenObtainService.getByAccessToken(token);
        } catch (final Exception e) {
            throw new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodes.INVALID_TOKEN, "获取令牌出错", null));
        }

        if (null == authentication) {
            throw new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodes.INVALID_TOKEN, "令牌无效或过期", null));
        }

        final Object principal = authentication.getPrincipal();

        @SuppressWarnings("unchecked")
        final Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authentication.getAuthorities();

        final Oauth2SecurityUser oAuth2AuthenticatedPrincipal = toOAuth2AuthenticatedPrincipal(principal);

        oAuth2AuthenticatedPrincipal.addAuthorities(authorities);

        return oAuth2AuthenticatedPrincipal;
    }

    /**
     * 把SecurityUser转化为Oauth2SecurityUser
     *
     * @param principal
     * @return Oauth2SecurityUser
     */
    private static Oauth2SecurityUser toOAuth2AuthenticatedPrincipal(Object principal) {
        final Oauth2SecurityUser user = new Oauth2SecurityUser();

        final Map<String, Object> attributes = new HashMap<>();

        try {
            final PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(Oauth2SecurityUser.class);

            for (final PropertyDescriptor targetPd : targetPds) {
                final Method writeMethod = targetPd.getWriteMethod();
                if (null == writeMethod) {
                    continue;
                }
                final PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(principal.getClass(),
                        targetPd.getName());
                if (sourcePd == null) {
                    continue;
                }
                final Method readMethod = sourcePd.getReadMethod();
                if (readMethod == null) {
                    continue;
                }
                final ResolvableType sourceResolvableType = ResolvableType.forMethodReturnType(readMethod);
                final ResolvableType targetResolvableType = ResolvableType.forMethodParameter(writeMethod, 0);

                // Ignore generic types in assignable check if either ResolvableType has unresolvable generics.
                final boolean isAssignable = (sourceResolvableType.hasUnresolvableGenerics()
                        || targetResolvableType.hasUnresolvableGenerics()
                        ? ClassUtils.isAssignable(writeMethod.getParameterTypes()[0],
                        readMethod.getReturnType())
                        : targetResolvableType.isAssignableFrom(sourceResolvableType));

                if (!isAssignable) {
                    continue;
                }
                if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                    readMethod.setAccessible(true);
                }
                final Object value = readMethod.invoke(principal);
                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                    writeMethod.setAccessible(true);
                }
                writeMethod.invoke(user, value);
                if (null != value) {
                    final String propertyName = targetPd.getName();
                    attributes.put(propertyName, value);
                }
            }

            user.setAttributes(attributes);
        } catch (final Exception e) {
            throw new OAuth2AuthenticationException(
                    new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR, "登录信息转换出错", null), e);
        }
        return user;
    }

}
