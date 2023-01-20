package com.jarvis.platform.app.util;

import com.jarvis.framework.core.exception.BusinessException;

/**
 * 抛出异常类
 *
 * @author hewm
 * @date 2022/9/9
 */
public final class BusinessAssert {

    /**
     * 如果不为真就抛出异常
     *
     * @param expression       判断值
     * @param errorMsgTemplate 错误抛出异常附带的消息模板，变量用{}代替
     * @param params           参数列表
     */
    public static void isTure(boolean expression, String errorMsgTemplate, Object... params) {
        if (!expression) {
            throw new BusinessException(StrUtil.format(errorMsgTemplate, params));
        }
    }

    /**
     * 不为空
     *
     * @param object           对象
     * @param errorMsgTemplate 错误抛出异常附带的消息模板，变量用{}代替
     * @param params           参数列表
     */
    public static void notNull(Object object, String errorMsgTemplate, Object... params) {
        if (object == null) {
            throw new BusinessException(StrUtil.format(errorMsgTemplate, params));
        }
    }

}
