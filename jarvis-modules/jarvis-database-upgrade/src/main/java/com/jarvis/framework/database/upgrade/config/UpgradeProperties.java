package com.jarvis.framework.database.upgrade.config;

import com.jarvis.framework.database.upgrade.model.DatabaseUpgrade;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月1日
 */
@ConfigurationProperties
public class UpgradeProperties {

    /**
     * 升级脚本集合
     */
    private List<DatabaseUpgrade> databaseUpgrades;

    /**
     * @return the databaseUpgrades
     */
    public List<DatabaseUpgrade> getDatabaseUpgrades() {
        return databaseUpgrades;
    }

    /**
     * @param databaseUpgrades the databaseUpgrades to set
     */
    public void setDatabaseUpgrades(List<DatabaseUpgrade> databaseUpgrades) {
        this.databaseUpgrades = databaseUpgrades;
    }

}
