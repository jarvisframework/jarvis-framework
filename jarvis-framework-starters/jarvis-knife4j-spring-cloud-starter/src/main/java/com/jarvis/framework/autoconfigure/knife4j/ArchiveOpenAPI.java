package com.jarvis.framework.autoconfigure.knife4j;

import io.swagger.v3.oas.models.OpenAPI;

public class ArchiveOpenAPI extends OpenAPI {
    private String basePath;
    private String host;

    public ArchiveOpenAPI() {
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getBasePath() {
        return this.basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
