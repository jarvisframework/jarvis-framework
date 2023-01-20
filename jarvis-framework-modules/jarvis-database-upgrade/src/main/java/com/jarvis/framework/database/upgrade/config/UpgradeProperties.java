package com.jarvis.framework.database.upgrade.config;

import com.jarvis.framework.database.upgrade.model.DatabaseUpgrade;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties
public class UpgradeProperties {

    private List<DatabaseUpgrade> databaseUpgrades;

    public List<DatabaseUpgrade> getDatabaseUpgrades() {
        return this.databaseUpgrades;
    }

    public void setDatabaseUpgrades(List<DatabaseUpgrade> databaseUpgrades) {
        this.databaseUpgrades = databaseUpgrades;
    }
}
