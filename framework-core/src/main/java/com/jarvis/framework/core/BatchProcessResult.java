package com.jarvis.framework.core;

import java.util.List;

/**
 * <p>批处理结果对象</p>
 *
 * @author 王涛
 * @since 1.0, 2021-01-06 18:12:05
 */
public class BatchProcessResult<S, E> {

    /**
     * 处理成功条数
     */
    private List<S> successList;

    /**
     * 处理失败条数
     */
    private List<E> failureList;

    /**
     * 无参构造器
     */
    public BatchProcessResult() {
    }

    /**
     * 全参构造器
     *
     * @param successList
     * @param failureList
     */
    public BatchProcessResult(List<S> successList, List<E> failureList) {
        this.successList = successList;
        this.failureList = failureList;
    }

    public List<S> getSuccessList() {
        return successList;
    }

    public void setSuccessList(List<S> successList) {
        this.successList = successList;
    }

    public List<E> getFailureList() {
        return failureList;
    }

    public void setFailureList(List<E> failureList) {
        this.failureList = failureList;
    }
}
