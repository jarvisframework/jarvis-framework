package com.jarvis.framework.bizlog;

import com.jarvis.framework.bizlog.entity.BizLog;
import org.springframework.context.ApplicationEvent;

public class BizLogEvent extends ApplicationEvent {
    private static final long serialVersionUID = -5111625734657324565L;

    public BizLogEvent(BizLog source) {
        super(source);
    }

    public BizLog getSource() {
        return (BizLog)this.source;
    }
}
