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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class CriteriaQueryMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private final String queryPrefix = "Q_";
    private static final Map<Class<?>, Class<?>> controllerEntityMap = new ConcurrentHashMap();

    public boolean supportsParameter(MethodParameter parameter) {
        return CriteriaQuery.class.isAssignableFrom(parameter.getParameterType());
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Class<?> containingClass = parameter.getContainingClass();
        Class<?> entityClass = this.getEntityClazz(containingClass);
        Object valueInstance = parameter.getParameterType().newInstance();
        List<ConditionExpression> conditions = ((CriteriaQuery)valueInstance).getFilter().getConditionExpressions();
        WebDataBinder webDataBinder = binderFactory.createBinder(webRequest, (Object)null, (String)null);
        ConversionService conversionService = webDataBinder.getConversionService();
        this.processQueryParameters((ServletRequest)webRequest.getNativeRequest(ServletRequest.class), entityClass, conditions, conversionService);
        return valueInstance;
    }

    private Class<?> getEntityClazz(Class<?> containingClass) {
        Class<?> entityClazz = (Class)controllerEntityMap.get(containingClass);
        if (null == entityClazz) {
            Map<TypeVariable, Type> typeVariableMap = GenericTypeResolver.getTypeVariableMap(containingClass);
            if (null != typeVariableMap && !typeVariableMap.isEmpty()) {
                Iterator var4 = typeVariableMap.entrySet().iterator();

                while(var4.hasNext()) {
                    Entry<TypeVariable, Type> entry = (Entry)var4.next();
                    if ("Entity".equals(((TypeVariable)entry.getKey()).getName())) {
                        entityClazz = (Class)entry.getValue();
                        break;
                    }
                }
            } else {
                entityClazz = Object.class;
            }

            controllerEntityMap.putIfAbsent(containingClass, entityClazz);
        }

        return entityClazz;
    }

    private void processQueryParameters(ServletRequest request, Class<?> entityClass, List<ConditionExpression> conditions, ConversionService conversionService) {
        Enumeration paramNames = request.getParameterNames();

        while(paramNames != null && paramNames.hasMoreElements()) {
            String paramName = (String)paramNames.nextElement();
            if (paramName.startsWith("Q_")) {
                String[] arrayValue = request.getParameterValues(paramName);
                if (null != arrayValue && 0 != arrayValue.length && StringUtils.hasText(arrayValue[0])) {
                    conditions.add(this.toSingleCondition(entityClass, paramName, arrayValue, conversionService));
                }
            }
        }

    }

    private SingleCondition<String, Object> toSingleCondition(Class<?> entityClass, String paramName, String[] arrayValue, ConversionService conversionService) {
        String unprefixed = paramName.substring("Q_".length());
        String[] array = unprefixed.split("_");
        if (2 != array.length && 3 != array.length) {
            throw new FrameworkException("查询参数格式不对，示例：Q_EQ_fondsCode=SH001/Q_EQ_fondsCode_C=SH001");
        } else {
            CriteriaQueryMethodArgumentResolver.QueryValueTypeEnum typeEnum = null;
            if (3 == array.length) {
                typeEnum = CriteriaQueryMethodArgumentResolver.QueryValueTypeEnum.valueOf(array[2]);
            }

            ConditionOperatorEnum op = ConditionOperatorEnum.valueOf(array[0]);
            if (null == op) {
                throw new FrameworkException("查询参数格式不对，不支持操作符[" + array[0] + "]");
            } else {
                String prop = array[1];
                Object value = null;
                if (ConditionOperatorEnum.NULL != op && ConditionOperatorEnum.NNULL != op) {
                    if (null == arrayValue) {
                        throw new FrameworkException("查询参数[" + paramName + "]值不能为空");
                    }

                    String paramValue = arrayValue[0];
                    value = this.processQueryValue(entityClass, typeEnum, op, prop, paramValue, conversionService);
                }

                return Character.isUpperCase(prop.charAt(0)) ? new SingleCondition(CamelCaseUtil.upperToLowerCamelCase(prop), op, value) : new SingleCondition(ColumnFunctionUtil.fieldToColumn(prop), op, value);
            }
        }
    }

    private Object processQueryValue(Class<?> entityClass, CriteriaQueryMethodArgumentResolver.QueryValueTypeEnum typeEnum, ConditionOperatorEnum op, String column, String paramValue, ConversionService conversionService) {
        if (ConditionOperatorEnum.BT != op && ConditionOperatorEnum.IN != op && ConditionOperatorEnum.NIN != op) {
            return this.processQueryValue(entityClass, typeEnum, column, paramValue, conversionService);
        } else {
            String[] arrayStr = paramValue.split(",");
            if (2 != arrayStr.length) {
                throw new FrameworkException("between条件参数值格式：aaaa,bbbb");
            } else {
                BetweenValue value = new BetweenValue(this.processQueryValue(entityClass, typeEnum, column, arrayStr[0], conversionService), this.processQueryValue(entityClass, typeEnum, column, arrayStr[1], conversionService));
                return value;
            }
        }
    }

    private Object processQueryValue(Class<?> entityClass, CriteriaQueryMethodArgumentResolver.QueryValueTypeEnum typeEnum, String column, String paramValue, ConversionService conversionService) {
        if (null != typeEnum) {
            return this.processQueryValue(typeEnum, paramValue, conversionService);
        } else if (Object.class.equals(entityClass)) {
            return paramValue;
        } else {
            Field field = ReflectionUtils.findField(entityClass, column);
            if (null == field) {
                return paramValue;
            } else {
                Class<?> type = field.getType();
                return conversionService.convert(paramValue, type);
            }
        }
    }

    private Object processQueryValue(CriteriaQueryMethodArgumentResolver.QueryValueTypeEnum typeEnum, String paramValue, ConversionService conversionService) {
        switch(typeEnum) {
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

    private static enum QueryValueTypeEnum {
        C,
        I,
        L,
        BD,
        D,
        T,
        DT;
    }
}
