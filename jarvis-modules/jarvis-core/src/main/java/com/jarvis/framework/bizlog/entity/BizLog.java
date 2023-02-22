package com.jarvis.framework.bizlog.entity;

import java.time.LocalDateTime;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年7月19日
 */
public class BizLog {

    /**
     * 模块名称
     */
    private String module;

    /**
     * 操作
     */
    private String action;

    /**
     * 日志内容
     */
    private String content;

    /**
     * 执行时间：单位(ms)
     */
    private long duration;

    /**
     * 操作IP地址
     */
    private String remoteAddr;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 请求URI
     */
    private String requestUri;

    /**
     * 请求method
     */
    private String method;

    /**
     * 操作是否成功: 0-否，1-是
     */
    private int success = 1;

    /**
     * 系统ID
     */
    private String systemId;

    /**
     * 创建用户ID
     */
    private Object userDetails;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime = LocalDateTime.now();

    /**
     * @return the module
     */
    public String getModule() {
        return module;
    }

    /**
     * @param module the module to set
     */
    public void setModule(String module) {
        this.module = module;
    }

    /**
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the duration
     */
    public long getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(long duration) {
        this.duration = duration;
    }

    /**
     * @return the remoteAddr
     */
    public String getRemoteAddr() {
        return remoteAddr;
    }

    /**
     * @param remoteAddr the remoteAddr to set
     */
    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    /**
     * @return the userAgent
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * @param userAgent the userAgent to set
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * @return the requestUri
     */
    public String getRequestUri() {
        return requestUri;
    }

    /**
     * @param requestUri the requestUri to set
     */
    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    /**
     * @return the method
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @return the success
     */
    public int getSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(int success) {
        this.success = success;
    }

    /**
     * @return the systemId
     */
    public String getSystemId() {
        return systemId;
    }

    /**
     * @param systemId the systemId to set
     */
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    /**
     * @return the userDetails
     */
    public Object getUserDetails() {
        return userDetails;
    }

    /**
     * @param userDetails the userDetails to set
     */
    public void setUserDetails(Object userDetails) {
        this.userDetails = userDetails;
    }

    /**
     * @return the createdTime
     */
    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    /**
     * @param createdTime the createdTime to set
     */
    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    /**
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BizLog [module=" + module + ", action=" + action + ", content=" + content + ", duration=" + duration
                + ", remoteAddr=" + remoteAddr + ", userAgent=" + userAgent + ", requestUri=" + requestUri + ", method="
                + method + ", success=" + success + ", systemId=" + systemId + ", userDetails=" + userDetails
                + ", createdTime=" + createdTime + "]";
    }

}
