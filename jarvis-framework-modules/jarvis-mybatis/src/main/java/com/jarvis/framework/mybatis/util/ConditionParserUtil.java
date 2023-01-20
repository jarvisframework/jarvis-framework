package com.jarvis.framework.mybatis.util;

import java.util.List;

public class ConditionParserUtil {

    public static String getBindingField(String bindName, Object value, List<Object> params) {
        String bindingField = PersistentUtil.getBindingField(bindName + "." + "params[" + params.size() + "]");
        params.add(value);
        return bindingField;
    }
}
