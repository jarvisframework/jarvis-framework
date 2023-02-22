package com.jarvis.framework.web.thead;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年12月1日
 */
public class ContextCopyThreadPoolExecutor extends ThreadPoolTaskExecutor {

    /**
     *
     */
    private static final long serialVersionUID = -1392123770572111202L;

    public ContextCopyThreadPoolExecutor() {
        setTaskDecorator(new ContextCopyDecorator());
    }

}
