package com.jarvis.framework.bizlog;

import com.jarvis.framework.bizlog.entity.BizLog;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年7月20日
 */
public class BizLogEventListener {

    private final BizLogService bizLogService;

    public BizLogEventListener(BizLogService bizLogService) {
        this.bizLogService = bizLogService;
    }

    @Async
    @Order
    @EventListener(BizLogEvent.class)
    public void bizLogEvent(BizLogEvent bizLogEvent) {
        final BizLog source = bizLogEvent.getSource();

        bizLogService.save(source);
    }

}
