package com.jarvis.framework.core.scheduler;

import com.jarvis.framework.core.BatchProcessResult;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>调度响应</p>
 *
 * @author 王涛
 * @since 1.0, 2020-12-24 10:08:27
 */
public class SchedulerResponse implements Serializable {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 5837683815871765735L;

    /**
     * 成功标识
     */
    private Boolean success;
    /**
     * 下次执行参数时间
     */
    private Date nextTime;
    /**
     * 响应消息
     */
    private String message;
    /**
     * 批处理结果
     */
    private BatchProcessResult batch;

    /**
     * 是否成功的响应
     *
     * @return
     */
    public Boolean isSuccess() {
        return this.success;
    }

    /**
     * 是否失败的响应
     *
     * @return
     */
    public Boolean isFailure() {
        return !isSuccess();
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Date getNextTime() {
        return nextTime;
    }

    public void setNextTime(Date nextTime) {
        this.nextTime = nextTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BatchProcessResult getBatch() {
        return batch;
    }

    public void setBatch(BatchProcessResult batch) {
        this.batch = batch;
    }
}
