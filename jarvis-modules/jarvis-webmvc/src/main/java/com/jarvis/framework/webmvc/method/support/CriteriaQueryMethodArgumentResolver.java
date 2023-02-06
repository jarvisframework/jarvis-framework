package com.jarvis.framework.webmvc.method.support;

import com.jarvis.framework.core.exception.FrameworkException;
import com.jarvis.framework.search.BetweenValue;
import com.jarvis.framework.search.ConditionExpression;
import com.jarvis.framework.search.ConditionOperatorEnum;
import com.jarvis.framework.search.CriteriaQuery;
import com.jarvis.framework.search.SingleCondition;
import com.jarvis.framework.util.CamelCaseUtil;
import com.jarvis.framework.util.ColumnFunctionUtil;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.ServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * CriteriaQuery参数处理
 *
 * @author qiucs
 * @version 1.0.0 2021年2月4日
 */
public class CriteriaQueryMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final String queryPrefix = "Q_";

    //private final GenericConversionService conversionService;

    private static final Map<Class<?>, Class<?>> controllerEntityMap = new ConcurrentHashMap<Class<?>, Class<?>>();

    /*public CriteriaQueryMethodArgumentResolver(GenericConversionService conversionService) {
        super();
        this.conversionService = conversionService;
    }*/

    /**
     *
     * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#supportsParameter(org.springframework.core.MethodParameter)
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return CriteriaQuery.class.isAssignableFrom(parameter.getParameterType());
    }

    /**
     *
     * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#resolveArgument(org.springframework.core.MethodParameter,
     *      org.springframework.web.method.support.ModelAndViewContainer, org.springframework.web.context.request.NativeWebRequest,
     *      org.springframework.web.bind.support.WebDataBinderFactory)
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        final Class<?> containingClass = parameter.getContainingClass();
        final Class<?> entityClass = getEntityClazz(containingClass);

        final Object valueInstance = parameter.getParameterType().newInstance();
        final List<ConditionExpression> conditions = ((CriteriaQuery<?>) valueInstance).getFilter()
                .getConditionExpressions();
        final WebDataBinder webDataBinder = binderFactory.createBinder(webRequest, null, null);
        final ConversionService conversionService = webDataBinder.getConversionService();
        processQueryParameters(webRequest.getNativeRequest(ServletRequest.class), entityClass, conditions,
                conversionService);
        return valueInstance;
    }

    @SuppressWarnings("rawtypes")
    private Class<?> getEntityClazz(Class<?> containingClass) {
        Class<?> entityClazz = controllerEntityMap.get(containingClass);

        if (null == entityClazz) {
            final Map<TypeVariable, Type> typeVariableMap = GenericTypeResolver.getTypeVariableMap(containingClass);
            if (null != typeVariableMap && !typeVariableMap.isEmpty()) {
                for (final Entry<TypeVariable, Type> entry : typeVariableMap.entrySet()) {
                    if (!"Entity".equals(entry.getKey().getName())) {
                        continue;
                    }
                    entityClazz = (Class<?>) entry.getValue();
                    break;
                }
            }
            if (null == entityClazz) {
                entityClazz = Object.class;
            }
            controllerEntityMap.putIfAbsent(containingClass, entityClazz);
        }
        return entityClazz;
    }

    private void processQueryParameters(final ServletRequest request, Class<?> entityClass,
                                        List<ConditionExpression> conditions, ConversionService conversionService) {
        final Enumeration<String> paramNames = request.getParameterNames();

        String[] arrayValue;
        String paramName;
        while (paramNames != null && paramNames.hasMoreElements()) {
            paramName = paramNames.nextElement();

            if (paramName.startsWith(queryPrefix)) {
                arrayValue = request.getParameterValues(paramName);
                if (null == arrayValue || 0 == arrayValue.length || !StringUtils.hasText(arrayValue[0])) {
                    continue;
                }
                conditions.add(toSingleCondition(entityClass, paramName, arrayValue, conversionService));
            }
        }

    }

    private SingleCondition<String, Object> toSingleCondition(Class<?> entityClass, String paramName,
                                                              String[] arrayValue, ConversionService conversionService) {
        final String unprefixed = paramName.substring(queryPrefix.length());
        final String[] array = unprefixed.split("_");
        if (2 != array.length && 3 != array.length) {
            throw new FrameworkException("查询参数格式不对，示例：Q_EQ_fondsCode=SH001/Q_EQ_fondsCode_C=SH001");
        }
        QueryValueTypeEnum typeEnum = null;
        if (3 == array.length) {
            typeEnum = QueryValueTypeEnum.valueOf(array[2]);
        }
        final ConditionOperatorEnum op = ConditionOperatorEnum.valueOf(array[0]);
        if (null == op) {
            throw new FrameworkException("查询参数格式不对，不支持操作符[" + array[0] + "]");
        }
        // 类字段属性
        final String prop = array[1];
        Object value = null;
        if (ConditionOperatorEnum.NULL != op && ConditionOperatorEnum.NNULL != op) {
            if (null == arrayValue) {
                throw new FrameworkException("查询参数[" + paramName + "]值不能为空");
            }
            final String paramValue = arrayValue[0];
            value = processQueryValue(entityClass, typeEnum, op, prop, paramValue, conversionService);
        }
        // 如果是大写驼峰，则是转成小写驼峰，由后台转成字段
        if (Character.isUpperCase(prop.charAt(0))) {
            return new SingleCondition<String, Object>(CamelCaseUtil.upperToLowerCamelCase(prop), op, value);
        }

        // 如果是小写驼峰，则是转成字段
        return new SingleCondition<String, Object>(ColumnFunctionUtil.fieldToColumn(prop), op, value);
    }

    private Object processQueryValue(Class<?> entityClass, QueryValueTypeEnum typeEnum, ConditionOperatorEnum op,
                                     String column, String paramValue, ConversionService conversionService) {
        if (ConditionOperatorEnum.BT == op || ConditionOperatorEnum.IN == op || ConditionOperatorEnum.NIN == op) {
            final String[] arrayStr = paramValue.split(",");
            if (ConditionOperatorEnum.BT != op) {
                if (null != typeEnum) {
                    final Object[] objs = new Object[arrayStr.length];
                    for (int i = 0, len = arrayStr.length; i < len; i++) {
                        objs[i] = processQueryValue(entityClass, typeEnum, column, arrayStr[i], conversionService);
                    }
                    return objs;
                }
                return arrayStr;
            }
            if (2 != arrayStr.length) {
                throw new FrameworkException("between条件参数值格式：aaaa,bbbb");
            }
            final BetweenValue value = new BetweenValue(
                    processQueryValue(entityClass, typeEnum, column, arrayStr[0], conversionService),
                    processQueryValue(entityClass, typeEnum, column, arrayStr[1], conversionService));
            return value;
        } else {
            return processQueryValue(entityClass, typeEnum, column, paramValue, conversionService);
        }
    }

    private Object processQueryValue(Class<?> entityClass, QueryValueTypeEnum typeEnum, String column,
                                     String paramValue, ConversionService conversionService) {
        if (null != typeEnum) {
            return processQueryValue(typeEnum, paramValue, conversionService);
        }
        if (Object.class.equals(entityClass)) {
            return paramValue;
        }
        final Field field = ReflectionUtils.findField(entityClass, column);
        if (null == field) {
            return paramValue;
        }
        final Class<?> type = field.getType();
        return conversionService.convert(paramValue, type);
    }

    private Object processQueryValue(QueryValueTypeEnum typeEnum, String paramValue,
                                     ConversionService conversionService) {
        switch (typeEnum) {
            case C:
                return paramValue;
            case I:
                return conversionService.convert(paramValue, Integer.class);
            case L:
                return conversionService.convert(paramValue, Long.class);
            case BD:
                return conversionService.convert(paramValue, BigDecimal.class);
            case D:
                return conversionService.convert(paramValue, LocalDate.class);
            case T:
                return conversionService.convert(paramValue, LocalTime.class);
            case DT:
                return conversionService.convert(paramValue, LocalDateTime.class);

            default:
                return paramValue;
        }
    }

    private enum QueryValueTypeEnum {
        /** 字符（CharSequence） */
        C,
        /** 数字（Integer） */
        I,
        /** 数字（Long） */
        L,
        /** 数字（BigDecail） */
        BD,
        /** 日期（LocalDate） */
        D,
        /** 时间（LocalTime） */
        T,
        /** 日期时间（LocalDateTime） */
        DT;
    }

}
