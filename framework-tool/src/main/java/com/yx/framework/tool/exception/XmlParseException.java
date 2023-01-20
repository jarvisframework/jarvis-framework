package com.yx.framework.tool.exception;

import com.jarvis.framework.core.exception.BusinessException;

/**
 * <p>Xml处理异常类</p>
 *
 * @author 王涛
 * @date 2019-11-18 17:39:54
 */
public class XmlParseException extends BusinessException {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -8151129878560445755L;

    public XmlParseException(String msg) {
        super(msg);
    }

}
