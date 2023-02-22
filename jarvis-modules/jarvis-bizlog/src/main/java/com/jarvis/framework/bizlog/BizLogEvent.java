package com.jarvis.framework.bizlog;

import com.jarvis.framework.bizlog.entity.BizLog;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年7月19日
 */
public class BizLogEvent extends ApplicationEvent {

    /**
     *
     */
    private static final long serialVersionUID = -5111625734657324565L;

    /**
     * @param source
     */
    public BizLogEvent(BizLog source) {
        super(source);
    }

    /**
     * The object on which the Event initially occurred.
     *
     * @return The object on which the Event initially occurred.
     */
    @Override
    public BizLog getSource() {
        return (BizLog) source;
    }

}
