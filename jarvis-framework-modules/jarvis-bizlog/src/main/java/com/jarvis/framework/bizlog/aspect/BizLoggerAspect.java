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

@Aspect
public class BizLoggerAspect {
    private final ApplicationEventPublisher publisher;
    private final BizLevel level;
    private final BizLogModel modelService;
    private final String delimiter;

    public BizLoggerAspect(BizLevel level, ApplicationEventPublisher publisher, BizLogModel modelService, String delimiter) {
        this.publisher = publisher;
        this.level = level;
        this.modelService = modelService;
        this.delimiter = delimiter;
    }

    @Around("@annotation(bizLogger)")
    public Object around(ProceedingJoinPoint point, BizLogger bizLogger) throws Throwable {
        Object var10;
        try {
            if (this.isPassLogger(bizLogger.level())) {
                var10 = point.proceed();
                return var10;
            }

            long start = System.currentTimeMillis();
            Object result = null;
            BizLog bizLog = BizLoggerUtil.createRequestBizLog();
            MethodExpressionRoot root = this.createExpressionRoot(point);

            try {
                result = point.proceed();
                root.setResult(result);
            } catch (Throwable var13) {
                root.setError(var13);
            }

            this.parseBizLogger(bizLog, bizLogger, start, root);
            this.publisher.publishEvent(new BizLogEvent(bizLog));
            if (root.getError() != null) {
                throw root.getError();
            }

            var10 = result;
        } finally {
            BizLogContentHolder.clearContent();
        }

        return var10;
    }

    private boolean isPassLogger(BizLevel bizLevel) {
        return this.level.ordinal() == 0 || bizLevel.ordinal() == 0 || bizLevel.ordinal() > this.level.ordinal();
    }

    private MethodExpressionRoot createExpressionRoot(ProceedingJoinPoint point) {
        MethodExpressionRoot root = new MethodExpressionRoot();
        Object[] args = point.getArgs();
        Object target = point.getTarget();
        MethodSignature signature = (MethodSignature)point.getSignature();
        root.setTarget(target);
        root.setArgs(args);
        root.setMethod(signature.getMethod());
        return root;
    }

    private void parseBizLogger(BizLog bizLog, BizLogger logger, long start, MethodExpressionRoot root) {
        String action;
        if (this.modelService != null) {
            action = this.modelService.getModule();
            if (action != null) {
                bizLog.setModule(action);
            }
        }

        if (bizLog.getModule() == null) {
            if (StringUtils.hasLength(logger.module())) {
                bizLog.setModule(logger.module());
            } else {
                action = this.modelFromSwagger(root.getTarget().getClass());
                if (action == null) {
                    action = root.getTarget().getClass().getSimpleName();
                }

                bizLog.setModule(action);
            }
        }

        if (StringUtils.hasLength(logger.action())) {
            bizLog.setAction(logger.action());
        } else {
            action = this.actionFromSwagger(root.getMethod());
            if (action == null) {
                action = root.getMethod().getName();
            }

            bizLog.setAction(action);
        }

        if (root.getError() != null) {
            bizLog.setSuccess(0);
            bizLog.setContent("操作出错：" + root.getError().getMessage());
        } else {
            bizLog.setContent(this.getContent(logger, root));
        }

        bizLog.setDuration(System.currentTimeMillis() - start);
    }

    private String getContent(BizLogger logger, MethodExpressionRoot root) {
        List<String> list = BizLogContentHolder.getContent();
        if (list != null && !list.isEmpty()) {
            return StringUtils.collectionToDelimitedString(list, this.delimiter);
        } else {
            String content = logger.content();
            return StringUtils.hasLength(content) ? (String) ApplicationContextUtil.parseTemplateExpression(content, root, String.class) : WebUtil.request().getQueryString();
        }
    }

    private String modelFromSwagger(Class<?> targetClazz) {
        Api api = (Api)AnnotationUtils.findAnnotation(targetClazz, Api.class);
        if (api == null) {
            return null;
        } else {
            String[] tags = api.tags();
            return tags != null && tags.length > 0 ? tags[0] : api.value();
        }
    }

    private String actionFromSwagger(Method method) {
        ApiOperation operation = (ApiOperation)AnnotationUtils.findAnnotation(method, ApiOperation.class);
        return operation == null ? null : operation.value();
    }
}
