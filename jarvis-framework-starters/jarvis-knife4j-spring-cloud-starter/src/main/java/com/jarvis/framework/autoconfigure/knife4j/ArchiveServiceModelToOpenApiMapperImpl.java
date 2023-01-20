package com.jarvis.framework.autoconfigure.knife4j;

import com.jarvis.framework.autoconfigure.springfox.ArchiveSpringfoxProperties;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import springfox.documentation.oas.mappers.ServiceModelToOpenApiMapperImpl;
import springfox.documentation.service.Documentation;

public class ArchiveServiceModelToOpenApiMapperImpl extends ServiceModelToOpenApiMapperImpl {
    @Autowired
    private ArchiveSpringfoxProperties properties;

    public ArchiveServiceModelToOpenApiMapperImpl() {
    }

    public OpenAPI mapDocumentation(Documentation from) {
        ArchiveOpenAPI archiveOpenAPI = new ArchiveOpenAPI();
        OpenAPI mapDocumentation = super.mapDocumentation(from);
        BeanUtils.copyProperties(mapDocumentation, archiveOpenAPI);
        archiveOpenAPI.setBasePath(this.properties.getServiceName());
        archiveOpenAPI.setHost(this.properties.getServiceHost());
        return archiveOpenAPI;
    }
}
