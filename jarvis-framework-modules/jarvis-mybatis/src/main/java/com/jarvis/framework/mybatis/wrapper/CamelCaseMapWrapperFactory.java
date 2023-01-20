package com.jarvis.framework.mybatis.wrapper;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

import java.util.Map;

public class CamelCaseMapWrapperFactory implements ObjectWrapperFactory {

    public boolean hasWrapperFor(Object object) {
        return object != null && object instanceof Map;
    }

    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        return new CamelCaseMapWrapper(metaObject, (Map)object);
    }
}
