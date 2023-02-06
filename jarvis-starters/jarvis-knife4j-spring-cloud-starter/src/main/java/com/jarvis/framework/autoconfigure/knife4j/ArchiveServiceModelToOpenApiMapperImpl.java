package com.jarvis.framework.autoconfigure.knife4j;

import com.jarvis.framework.autoconfigure.springfox.ArchiveSpringfoxProperties;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import springfox.documentation.oas.mappers.ServiceModelToOpenApiMapperImpl;
import springfox.documentation.service.Documentation;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年8月2日
 */
public class ArchiveServiceModelToOpenApiMapperImpl extends ServiceModelToOpenApiMapperImpl {

    @Autowired
    private ArchiveSpringfoxProperties properties;

    /**
     *
     * @see springfox.documentation.oas.mappers.ServiceModelToOpenApiMapperImpl#mapDocumentation(springfox.documentation.service.Documentation)
     */
    @Override
    public OpenAPI mapDocumentation(Documentation from) {
        final ArchiveOpenAPI archiveOpenAPI = new ArchiveOpenAPI();
        final OpenAPI mapDocumentation = super.mapDocumentation(from);
        BeanUtils.copyProperties(mapDocumentation, archiveOpenAPI);
        archiveOpenAPI.setBasePath(properties.getServiceName());
        archiveOpenAPI.setHost(properties.getServiceHost());
        return archiveOpenAPI;
    }

}
