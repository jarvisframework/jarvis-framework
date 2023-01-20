package com.jarvis.framework.autoconfigure.upgrade;

import com.jarvis.framework.database.upgrade.config.UpgradeProperties;
import com.jarvis.framework.database.upgrade.convert.DelegateFunctionConverter;
import com.jarvis.framework.database.upgrade.convert.DelegateTableConverter;
import com.jarvis.framework.database.upgrade.convert.FunctionConverter;
import com.jarvis.framework.database.upgrade.convert.TableConverter;
import com.jarvis.framework.database.upgrade.runner.UpgradeProcessorApplicationRunner;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Configuration
@ConditionalOnClass({UpgradeProcessorApplicationRunner.class})
@EnableConfigurationProperties({UpgradeProperties.class})
public class ArchiveDatabaseUpgradeAutoConfiguration implements InitializingBean {
    @Autowired(
        required = false
    )
    private TableConverter tableConverter;
    @Autowired(
        required = false
    )
    private List<FunctionConverter> functionConverters;

    @Bean
    UpgradeProcessorApplicationRunner upgradeProcessorApplicationRunner(DataSource a, UpgradeProperties a) throws SQLException {
        return new UpgradeProcessorApplicationRunner(a, a);
    }

    public ArchiveDatabaseUpgradeAutoConfiguration() {
    }

    public void afterPropertiesSet() throws Exception {
        if (null != a.functionConverters) {
            DelegateFunctionConverter.registConverter(a.functionConverters);
        }

        if (null != a.tableConverter) {
            DelegateTableConverter.registConverter(a.tableConverter);
        }

    }
}
