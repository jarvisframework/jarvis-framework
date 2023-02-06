package com.jarvis.framework.autoconfigure.bizlog;

import com.jarvis.framework.bizlog.constant.BizLevel;
import com.jarvis.framework.constant.SymbolConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年7月21日
 */
@ConfigurationProperties(prefix = "gdda.bizlog")
public class ArchiveBizLoggerProperties {

    /** 日志级别 */
    private BizLevel level = BizLevel.QUERY;

    /** 多条日志分隔符 */
    private String delimiter = SymbolConstant.SPACE;

    /**
     * @return the level
     */
    public BizLevel getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(BizLevel level) {
        this.level = level;
    }

    /**
     * @return the delimiter
     */
    public String getDelimiter() {
        return delimiter;
    }

    /**
     * @param delimiter the delimiter to set
     */
    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

}
