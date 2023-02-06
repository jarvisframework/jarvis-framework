package com.jarvis.framework.mybatis.wrapper;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

import java.util.Map;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年2月3日
 */
public class CamelCaseMapWrapperFactory implements ObjectWrapperFactory {

    /**
     *
     * @see org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory#hasWrapperFor(java.lang.Object)
     */
    @Override
    public boolean hasWrapperFor(Object object) {
        return object != null && object instanceof Map;
    }

    /**
     *
     * @see org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory#getWrapperFor(org.apache.ibatis.reflection.MetaObject, java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        return new CamelCaseMapWrapper(metaObject, (Map<String, Object>) object);
    }

}
