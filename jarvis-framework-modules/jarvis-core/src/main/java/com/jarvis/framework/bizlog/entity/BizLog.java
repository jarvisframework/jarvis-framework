package com.jarvis.framework.bizlog.entity;

import java.time.LocalDateTime;

/**
 * <p>业务日志实体</p>
 *
 * @author Doug Wang
 * @since 1.8, 2022-12-15 18:07:39
 */
public class BizLog {

    private String module;

    private String action;

    private String content;

    private long duration;

    private String remoteAddr;

    private String userAgent;

    private String requestUri;

    private String method;

    private int success = 1;

    private String systemId;

    private Object userDetails;

    private LocalDateTime createdTime = LocalDateTime.now();

    public String getModule() {
        return this.module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getRemoteAddr() {
        return this.remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getRequestUri() {
        return this.requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getSuccess() {
        return this.success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public Object getUserDetails() {
        return this.userDetails;
    }

    public void setUserDetails(Object userDetails) {
        this.userDetails = userDetails;
    }

    public LocalDateTime getCreatedTime() {
        return this.createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "BizLog [module=" + this.module + ", action=" + this.action + ", content=" + this.content + ", duration=" + this.duration + ", remoteAddr=" + this.remoteAddr + ", userAgent=" + this.userAgent + ", requestUri=" + this.requestUri + ", method=" + this.method + ", success=" + this.success + ", systemId=" + this.systemId + ", userDetails=" + this.userDetails + ", createdTime=" + this.createdTime + "]";
    }

}
