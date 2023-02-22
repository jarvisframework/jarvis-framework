package com.jarvis.framework.web.thead;

import org.springframework.core.task.TaskDecorator;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年12月1日
 */
public class ContextCopyDecorator implements TaskDecorator {

    /**
     *
     * @see org.springframework.core.task.TaskDecorator#decorate(java.lang.Runnable)
     */
    @Override
    public Runnable decorate(Runnable runnable) {
        final RequestAttributes context = RequestContextHolder.currentRequestAttributes();
        final SecurityContext securityContext = SecurityContextHolder.getContext();

        return () -> {
            try {
                RequestContextHolder.setRequestAttributes(context);
                SecurityContextHolder.setContext(securityContext);
                runnable.run();
            } finally {
                SecurityContextHolder.clearContext();
                RequestContextHolder.resetRequestAttributes();
            }
        };
    }

}
