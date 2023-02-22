package com.jarvis.framework.mybatis.snowflake;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月18日
 */
@ConfigurationProperties(prefix = "snowflake")
public class SnowflakeIdProperties {

    /**
     * 机器ID，从0~31
     */
    private int workerId = 0;

    /**
     * 数据中心ID，从0~7
     */
    private int dataCenterId = 0;

    /**
     * @return the workerId
     */
    public int getWorkerId() {
        return workerId;
    }

    /**
     * @param workerId the workerId to set
     */
    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    /**
     * @return the dataCenterId
     */
    public int getDataCenterId() {
        return dataCenterId;
    }

    /**
     * @param dataCenterId the dataCenterId to set
     */
    public void setDataCenterId(int dataCenterId) {
        this.dataCenterId = dataCenterId;
    }

}
