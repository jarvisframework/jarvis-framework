package com.jarvis.framework.mybatis.snowflake;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

public class SnowflakeWorkIdHolder implements EnvironmentAware, InitializingBean {

    private static Logger log = LoggerFactory.getLogger(SnowflakeWorkIdHolder.class);

    private static long workerId = 0L;

    private Environment environment;

    public static long getWorkerId() {
        return workerId;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void afterPropertiesSet() throws Exception {
        workerId = (Long)this.environment.getProperty("snowflake.worker-id", Long.TYPE);
        if (log.isInfoEnabled()) {
            log.info("snowflake worker id: {}", workerId);
        }

    }
}
