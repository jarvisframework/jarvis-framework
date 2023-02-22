package com.jarvis.framework.mybatis.util;

import com.jarvis.framework.constant.SymbolConstant;

import java.util.List;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月21日
 */
public class ConditionParserUtil {

    /**
     * SQL解析值绑定
     *
     * @param bindName
     * @param value
     * @param params
     * @return
     */
    public static String getBindingField(String bindName, Object value, List<Object> params) {
        final String bindingField = PersistentUtil
                .getBindingField(bindName + SymbolConstant.DOT + "params[" + params.size() + "]");
        params.add(value);
        return bindingField;
    }

}
