package com.jarvis.framework.mybatis.util;

import com.jarvis.framework.core.entity.BaseDynamicEntity;
import com.jarvis.framework.core.entity.BaseIdPrimaryKeyEntity;
import com.jarvis.framework.core.entity.BaseSimpleEntity;
import com.jarvis.framework.core.exception.FrameworkException;
import com.jarvis.framework.search.ConditionExpression;
import com.jarvis.framework.search.ConditionOperatorEnum;
import com.jarvis.framework.search.CriteriaQuery;
import com.jarvis.framework.search.SingleCondition;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月27日
 */
public class EntityQueryUtil {

    @SuppressWarnings("unchecked")
    public static <Entity extends BaseIdPrimaryKeyEntity<?>> void processQuery(CriteriaQuery<?> criterion,
                                                                               Entity entity) {
        if (null == entity) {
            return;
        }
        final Class<? extends BaseIdPrimaryKeyEntity<?>> clazz = (Class<? extends BaseIdPrimaryKeyEntity<?>>) entity
                .getClass();
        final List<ConditionExpression> conditions = criterion.getFilter().getConditionExpressions();
        if (BaseDynamicEntity.class.isAssignableFrom(clazz)) {
            processDynamicQuery(conditions, (BaseDynamicEntity<?>) entity);
        } else {
            processSimpleQuery(conditions, clazz, (BaseSimpleEntity<?>) entity);
        }
    }

    private static <Entity extends BaseDynamicEntity<?>> void processDynamicQuery(List<ConditionExpression> conditions,
                                                                                  Entity entity) {
        entity.forEach((fieldName, value) -> {
            if (null == value) {
                return;
            }
            conditions.add(new SingleCondition<String, Object>(fieldName, ConditionOperatorEnum.EQ, value));
        });
    }

    private static void processSimpleQuery(List<ConditionExpression> conditions,
                                           Class<? extends BaseIdPrimaryKeyEntity<?>> clazz, BaseSimpleEntity<?> entity) {
        try {
            final BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            Object value;
            for (final PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
                value = pd.getReadMethod().invoke(entity);
                if (null == value || Class.class.isAssignableFrom(value.getClass())) {
                    continue;
                }
                conditions.add(new SingleCondition<String, Object>(pd.getName(), ConditionOperatorEnum.EQ, value));
            }
        } catch (final Exception e) {
            throw new FrameworkException("处理检索条件出错", e);
        }
    }

}
