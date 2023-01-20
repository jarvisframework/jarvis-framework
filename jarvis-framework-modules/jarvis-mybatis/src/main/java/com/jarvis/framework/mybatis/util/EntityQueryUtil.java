package com.jarvis.framework.mybatis.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;

public class EntityQueryUtil {

    public static <Entity extends BaseIdPrimaryKeyEntity<?>> void processQuery(CriteriaQuery<?> criterion, Entity entity) {
        if (null != entity) {
            Class<? extends BaseIdPrimaryKeyEntity<?>> clazz = entity.getClass();
            List<ConditionExpression> conditions = criterion.getFilter().getConditionExpressions();
            if (BaseDynamicEntity.class.isAssignableFrom(clazz)) {
                processDynamicQuery(conditions, (BaseDynamicEntity)entity);
            } else {
                processSimpleQuery(conditions, clazz, (BaseSimpleEntity)entity);
            }

        }
    }

    private static <Entity extends BaseDynamicEntity<?>> void processDynamicQuery(List<ConditionExpression> conditions, Entity entity) {
        entity.forEach((fieldName, value) -> {
            if (null != value) {
                conditions.add(new SingleCondition(fieldName, ConditionOperatorEnum.EQ, value));
            }
        });
    }

    private static void processSimpleQuery(List<ConditionExpression> conditions, Class<? extends BaseIdPrimaryKeyEntity<?>> clazz, BaseSimpleEntity<?> entity) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] var5 = beanInfo.getPropertyDescriptors();
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                PropertyDescriptor pd = var5[var7];
                Object value = pd.getReadMethod().invoke(entity);
                if (null != value && !Class.class.isAssignableFrom(value.getClass())) {
                    conditions.add(new SingleCondition(pd.getName(), ConditionOperatorEnum.EQ, value));
                }
            }

        } catch (Exception var9) {
            throw new FrameworkException("处理检索条件出错", var9);
        }
    }
}
