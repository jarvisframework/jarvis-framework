package com.jarvis.framework.mybatis.snowflake;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月17日
 */
public class SnowflakeWorkIdHolder implements EnvironmentAware, InitializingBean {

    private static Logger log = LoggerFactory.getLogger(SnowflakeWorkIdHolder.class);

    private static long workerId = 0L;

    private Environment environment;

    public static long getWorkerId() {
        return workerId;
    }

    /**
     *
     * @see org.springframework.context.EnvironmentAware#setEnvironment(org.springframework.core.env.Environment)
     */
    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /**
     *
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        workerId = environment.getProperty("snowflake.worker-id", long.class);

        if (log.isInfoEnabled()) {
            log.info("snowflake worker id: {}", workerId);
        }
    }

}
