package com.jarvis.framework.mybatis.snowflake;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
    prefix = "snowflake"
)
public class SnowflakeIdProperties {
    private int workerId = 0;
    private int dataCenterId = 0;

    public SnowflakeIdProperties() {
    }

    public int getWorkerId() {
        return this.workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public int getDataCenterId() {
        return this.dataCenterId;
    }

    public void setDataCenterId(int dataCenterId) {
        this.dataCenterId = dataCenterId;
    }
}
