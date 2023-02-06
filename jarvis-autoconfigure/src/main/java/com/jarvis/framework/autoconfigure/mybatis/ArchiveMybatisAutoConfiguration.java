package com.jarvis.framework.autoconfigure.mybatis;

import com.jarvis.framework.mybatis.handler.EntityAutoFillingHandler;
import com.jarvis.framework.mybatis.handler.EntityAutoFillingHolder;
import com.jarvis.framework.mybatis.mapping.DatabaseIdHolder;
import com.jarvis.framework.mybatis.plugin.PageInterceptor;
import com.jarvis.framework.mybatis.provider.JdbcDatabaseIdProvider;
import com.jarvis.framework.mybatis.snowflake.SnowflakeIdProperties;
import com.jarvis.framework.mybatis.snowflake.SnowflakeWorkIdHolder;
import com.jarvis.framework.mybatis.wrapper.CamelCaseMapWrapperFactory;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月26日
 */
@Configuration
@ConditionalOnClass({ SqlSessionFactory.class, SqlSessionFactoryBean.class })
@EnableConfigurationProperties({ SnowflakeIdProperties.class, DruidExtendProperties.class })
@AutoConfigureAfter({ MybatisAutoConfiguration.class })
public class ArchiveMybatisAutoConfiguration {

    public ArchiveMybatisAutoConfiguration(ObjectProvider<EntityAutoFillingHandler> entityAutoFillingProvider) {
        EntityAutoFillingHolder.setHandler(entityAutoFillingProvider.getIfAvailable());
    }

    @Bean
    public JdbcDatabaseIdProvider databaseIdProvider() {
        return new JdbcDatabaseIdProvider();
    }

    @Bean
    public DatabaseIdHolder databaseIdHolder(DataSource dataSource) {
        return new DatabaseIdHolder(dataSource);
    }

    @Bean
    public SnowflakeWorkIdHolder snowflakeWorkIdHolder() {
        return new SnowflakeWorkIdHolder();
    }

    @Bean
    public Interceptor pageInterceptor() {
        return new PageInterceptor();
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return new ConfigurationCustomizer() {

            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                configuration.setObjectWrapperFactory(new CamelCaseMapWrapperFactory());
            }
        };
    }
}
