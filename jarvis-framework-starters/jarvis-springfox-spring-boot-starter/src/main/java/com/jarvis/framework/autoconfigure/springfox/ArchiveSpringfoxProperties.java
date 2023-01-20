package com.jarvis.framework.autoconfigure.springfox;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("springfox.documentation")
public class ArchiveSpringfoxProperties {
    private String version = "4.x";
    private String title = "档案系统Api文档";
    private String description = "档案一体化平台系统Api接口文档";
    private String serviceUrl = "http://localhost:9999";
    private String license;
    private String licenseUrl;
    private String serviceName;
    private String serviceHost;

    public ArchiveSpringfoxProperties() {
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServiceUrl() {
        return this.serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getLicense() {
        return this.license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getLicenseUrl() {
        return this.licenseUrl;
    }

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceHost() {
        return this.serviceHost;
    }

    public void setServiceHost(String serviceHost) {
        this.serviceHost = serviceHost;
    }
}
