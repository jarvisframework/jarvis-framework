package com.jarvis.framework.autoconfigure.springfox;

import com.jarvis.framework.search.CriteriaQuery;
import com.jarvis.framework.search.DynamicEntityQuery;
import com.jarvis.framework.search.EntityQuery;
import com.jarvis.framework.search.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.builders.RequestHandlerSelectors.withClassAnnotation;
import static springfox.documentation.builders.RequestHandlerSelectors.withMethodAnnotation;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年7月22日
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(value = "springfox.documentation.enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnWebApplication
@EnableConfigurationProperties(ArchiveSpringfoxProperties.class)
public class ArchiveSpringfoxAutoConfiguration {

    @Autowired
    private ArchiveSpringfoxProperties properties;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .ignoredParameterTypes(Page.class, CriteriaQuery.class, EntityQuery.class, DynamicEntityQuery.class)
                .apiInfo(apiInfo())
                .select()
                .apis(withClassAnnotation(Api.class).or(withMethodAnnotation(ApiOperation.class)))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(properties.getTitle())
                .description(properties.getDescription())
                .version(properties.getVersion())
                .termsOfServiceUrl(properties.getServiceUrl())
                .license(properties.getLicense())
                .licenseUrl(properties.getLicenseUrl())
                .build();
    }
}
