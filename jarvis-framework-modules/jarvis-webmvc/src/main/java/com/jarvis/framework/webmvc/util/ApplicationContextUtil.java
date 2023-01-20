package com.jarvis.framework.webmvc.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.expression.BeanExpressionContextAccessor;
import org.springframework.context.expression.BeanFactoryAccessor;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.expression.EnvironmentAccessor;
import org.springframework.context.expression.MapAccessor;
import org.springframework.core.convert.ConversionService;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.expression.spel.support.StandardTypeConverter;
import org.springframework.expression.spel.support.StandardTypeLocator;
import org.springframework.format.support.FormattingConversionService;

public class ApplicationContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    private static final ExpressionParser expressionParser = new SpelExpressionParser(new SpelParserConfiguration(true, true));
    private static StandardEvaluationContext evaluationContext;

    public ApplicationContextUtil() {
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtil.applicationContext = applicationContext;
        evaluationContext = initEvaluationContext(applicationContext);
    }

    private static StandardEvaluationContext initEvaluationContext(ApplicationContext applicationContext) {
        ConfigurableBeanFactory beanFactory = ((ConfigurableApplicationContext) applicationContext).getBeanFactory();
        BeanExpressionContext context = new BeanExpressionContext(beanFactory, (Scope) null);
        StandardEvaluationContext sec = new StandardEvaluationContext(context);
        sec.addPropertyAccessor(new BeanExpressionContextAccessor());
        sec.addPropertyAccessor(new BeanFactoryAccessor());
        sec.addPropertyAccessor(new MapAccessor());
        sec.addPropertyAccessor(new EnvironmentAccessor());
        sec.setBeanResolver(new BeanFactoryResolver(context.getBeanFactory()));
        sec.setTypeLocator(new StandardTypeLocator(context.getBeanFactory().getBeanClassLoader()));
        ConversionService conversionService = (ConversionService) getBean(FormattingConversionService.class);
        if (null == conversionService) {
            conversionService = context.getBeanFactory().getConversionService();
        }

        if (conversionService != null) {
            sec.setTypeConverter(new StandardTypeConverter(conversionService));
        }

        return sec;
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static Object parseTemplateExpression(String expression) throws ParseException {
        return expressionParser.parseExpression(expression, ParserContext.TEMPLATE_EXPRESSION).getValue(evaluationContext);
    }

    public static <T> T parseTemplateExpression(String expression, Class<T> classType) throws ParseException {
        return expressionParser.parseExpression(expression, ParserContext.TEMPLATE_EXPRESSION).getValue(evaluationContext, classType);
    }

    public static Object parseTemplateExpression(String expression, Object rootObject) throws ParseException {
        return expressionParser.parseExpression(expression, ParserContext.TEMPLATE_EXPRESSION).getValue(evaluationContext, rootObject);
    }

    public static <T> T parseTemplateExpression(String expression, Object rootObject, Class<T> classType) throws ParseException {
        return expressionParser.parseExpression(expression, ParserContext.TEMPLATE_EXPRESSION).getValue(evaluationContext, rootObject, classType);
    }

    public static Object parseExpression(String expression) throws ParseException {
        return expressionParser.parseExpression(expression).getValue(evaluationContext);
    }

    public static <T> T parseExpression(String expression, Class<T> classType) throws ParseException {
        return expressionParser.parseExpression(expression).getValue(evaluationContext, classType);
    }

    public static Object parseExpression(String expression, Object rootObject) throws ParseException {
        return expressionParser.parseExpression(expression).getValue(evaluationContext, rootObject);
    }

    public static <T> T parseExpression(String expression, Object rootObject, Class<T> classType) throws ParseException {
        return expressionParser.parseExpression(expression).getValue(evaluationContext, rootObject, classType);
    }
}
