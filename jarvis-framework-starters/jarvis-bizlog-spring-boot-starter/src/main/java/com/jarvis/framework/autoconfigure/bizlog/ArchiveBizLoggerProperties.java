package com.jarvis.framework.autoconfigure.bizlog;

import com.jarvis.framework.bizlog.constant.BizLevel;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
    prefix = "gdda.bizlog"
)
public class ArchiveBizLoggerProperties {
    private BizLevel level;
    private String delimiter;

    public ArchiveBizLoggerProperties() {
        this.level = BizLevel.QUERY;
        this.delimiter = " ";
    }

    public BizLevel getLevel() {
        return this.level;
    }

    public void setLevel(BizLevel level) {
        this.level = level;
    }

    public String getDelimiter() {
        return this.delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }
}
