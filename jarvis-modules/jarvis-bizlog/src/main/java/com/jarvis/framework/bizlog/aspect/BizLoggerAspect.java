package com.jarvis.framework.bizlog.aspect;

import com.jarvis.framework.bizlog.BizLogContentHolder;
import com.jarvis.framework.bizlog.BizLogEvent;
import com.jarvis.framework.bizlog.BizLogModel;
import com.jarvis.framework.bizlog.annotation.BizLogger;
import com.jarvis.framework.bizlog.constant.BizLevel;
import com.jarvis.framework.bizlog.entity.BizLog;
import com.jarvis.framework.bizlog.util.BizLoggerUtil;
import com.jarvis.framework.expression.MethodExpressionRoot;
import com.jarvis.framework.webmvc.util.ApplicationContextUtil;
import com.jarvis.framework.webmvc.util.WebUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年7月19日
 */
@Aspect
public class BizLoggerAspect {

    private final ApplicationEventPublisher publisher;

    private final BizLevel level;

    private final BizLogModel modelService;

    private final String delimiter;

    public BizLoggerAspect(BizLevel level, ApplicationEventPublisher publisher, BizLogModel modelService,
                           String delimiter) {
        this.publisher = publisher;
        this.level = level;
        this.modelService = modelService;
        this.delimiter = delimiter;
    }

    @Around("@annotation(bizLogger)")
    public Object around(ProceedingJoinPoint point, BizLogger bizLogger) throws Throwable {

        try {
            if (isPassLogger(bizLogger.level())) {
                return point.proceed();
            }

            final long start = System.currentTimeMillis();
            Object result = null;
            final BizLog bizLog = BizLoggerUtil.createRequestBizLog();
            final MethodExpressionRoot root = createExpressionRoot(point);

            try {
                result = point.proceed();
                root.setResult(result);
            } catch (final Throwable e) {
                root.setError(e);
            }
            parseBizLogger(bizLog, bizLogger, start, root);
            publisher.publishEvent(new BizLogEvent(bizLog));

            // 出错还原异常
            if (null != root.getError()) {
                throw root.getError();
            }

            return result;
        } finally {
            BizLogContentHolder.clearContent();
        }

    }

    private boolean isPassLogger(BizLevel bizLevel) {
        return 0 == level.ordinal() || 0 == bizLevel.ordinal() || bizLevel.ordinal() > level.ordinal();
    }

    private MethodExpressionRoot createExpressionRoot(ProceedingJoinPoint point) {
        final MethodExpressionRoot root = new MethodExpressionRoot();

        final Object[] args = point.getArgs();
        final Object target = point.getTarget();
        final MethodSignature signature = (MethodSignature) point.getSignature();

        root.setTarget(target);
        root.setArgs(args);
        root.setMethod(signature.getMethod());

        return root;
    }

    private void parseBizLogger(BizLog bizLog, BizLogger logger, long start, MethodExpressionRoot root) {
        // 由业务系统动态获取模块名称
        if (null != modelService) {
            final String module = modelService.getModule();
            if (null != module) {
                bizLog.setModule(module);
            }
        }
        if (null == bizLog.getModule()) {
            if (StringUtils.hasLength(logger.module())) {
                bizLog.setModule(logger.module());
            } else {
                String model = modelFromSwagger(root.getTarget().getClass());
                if (null == model) {
                    model = root.getTarget().getClass().getSimpleName();
                }
                bizLog.setModule(model);
            }
        }
        if (StringUtils.hasLength(logger.action())) {
            bizLog.setAction(logger.action());
        } else {
            String action = actionFromSwagger(root.getMethod());
            if (null == action) {
                action = root.getMethod().getName();
            }
            bizLog.setAction(action);
        }

        if (null != root.getError()) {
            bizLog.setSuccess(0);
            bizLog.setContent("操作出错：" + root.getError().getMessage());
        } else {
            bizLog.setContent(getContent(logger, root));
        }

        bizLog.setDuration(System.currentTimeMillis() - start);

    }

    private String getContent(BizLogger logger, MethodExpressionRoot root) {
        final List<String> list = BizLogContentHolder.getContent();
        if (null != list && !list.isEmpty()) {
            return StringUtils.collectionToDelimitedString(list, delimiter);
        }
        final String content = logger.content();
        if (StringUtils.hasLength(content)) {
            return (ApplicationContextUtil.parseTemplateExpression(content, root, String.class));
        }
        return (WebUtil.request().getQueryString());
    }

    private String modelFromSwagger(Class<?> targetClazz) {
        final Api api = AnnotationUtils.findAnnotation(targetClazz, Api.class);

        if (null == api) {
            return null;
        }

        final String[] tags = api.tags();
        if (null != tags && tags.length > 0) {
            return tags[0];
        }
        return api.value();
    }

    private String actionFromSwagger(Method method) {
        final ApiOperation operation = AnnotationUtils.findAnnotation(method, ApiOperation.class);
        if (null == operation) {
            return null;
        }

        return operation.value();
    }

}

