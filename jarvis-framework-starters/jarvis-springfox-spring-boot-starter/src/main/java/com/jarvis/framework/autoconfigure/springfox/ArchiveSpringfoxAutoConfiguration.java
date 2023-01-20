package com.jarvis.framework.autoconfigure.springfox;

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
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration(
    proxyBeanMethods = false
)
@ConditionalOnProperty(
    value = {"springfox.documentation.enabled"},
    havingValue = "true",
    matchIfMissing = true
)
@ConditionalOnWebApplication
@EnableConfigurationProperties({ArchiveSpringfoxProperties.class})
public class ArchiveSpringfoxAutoConfiguration {
    @Autowired
    private ArchiveSpringfoxProperties properties;

    public ArchiveSpringfoxAutoConfiguration() {
    }

    @Bean
    public Docket createRestApi() {
        return (new Docket(DocumentationType.OAS_30)).apiInfo(this.apiInfo()).select().apis(RequestHandlerSelectors.withClassAnnotation(Api.class).or(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))).paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return (new ApiInfoBuilder()).title(this.properties.getTitle()).description(this.properties.getDescription()).version(this.properties.getVersion()).termsOfServiceUrl(this.properties.getServiceUrl()).license(this.properties.getLicense()).licenseUrl(this.properties.getLicenseUrl()).build();
    }
}
