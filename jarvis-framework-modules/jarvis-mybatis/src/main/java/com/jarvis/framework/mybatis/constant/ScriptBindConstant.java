package com.jarvis.framework.mybatis.constant;

import com.jarvis.framework.mybatis.parser.EntityProvideParser;

public class ScriptBindConstant {
    public static final String BIND_NAME = "_param";
    public static final String UPDATE_BIND_VALUE = "@" + EntityProvideParser.class.getName() + "@parseUpdate(_parameter)";
    public static final String DELETE_BIND_VALUE = "@" + EntityProvideParser.class.getName() + "@parseDelete(_parameter)";
    public static final String QUERY_BIND_VALUE = "@" + EntityProvideParser.class.getName() + "@parseQuery(_parameter)";
}
