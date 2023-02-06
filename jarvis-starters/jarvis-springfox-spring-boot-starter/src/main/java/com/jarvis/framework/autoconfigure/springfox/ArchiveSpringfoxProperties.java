package com.jarvis.framework.autoconfigure.springfox;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年7月23日
 */
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

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the serviceUrl
     */
    public String getServiceUrl() {
        return serviceUrl;
    }

    /**
     * @param serviceUrl the serviceUrl to set
     */
    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    /**
     * @return the license
     */
    public String getLicense() {
        return license;
    }

    /**
     * @param license the license to set
     */
    public void setLicense(String license) {
        this.license = license;
    }

    /**
     * @return the licenseUrl
     */
    public String getLicenseUrl() {
        return licenseUrl;
    }

    /**
     * @param licenseUrl the licenseUrl to set
     */
    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    /**
     * @return the serviceName
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @param serviceName the serviceName to set
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * @return the serviceHost
     */
    public String getServiceHost() {
        return serviceHost;
    }

    /**
     * @param serviceHost the serviceHost to set
     */
    public void setServiceHost(String serviceHost) {
        this.serviceHost = serviceHost;
    }

}
