package com.jarvis.framework.autoconfigure.knife4j;

import io.swagger.v3.oas.models.OpenAPI;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年8月2日
 */
public class ArchiveOpenAPI extends OpenAPI {

    private String basePath;

    private String host;

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the basePath
     */
    public String getBasePath() {
        return basePath;
    }

    /**
     * @param basePath the basePath to set
     */
    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

}
