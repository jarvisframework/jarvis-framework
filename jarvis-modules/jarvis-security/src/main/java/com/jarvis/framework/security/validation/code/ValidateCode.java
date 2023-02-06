package com.jarvis.framework.security.validation.code;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月15日
 */
public class ValidateCode implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2750878912754284916L;

    private String code;

    private LocalDateTime expireTime;

    public ValidateCode(String code, int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    public boolean isExpried() {
        return LocalDateTime.now().isAfter(expireTime);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

}
