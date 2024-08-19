package com.jarvis.framework.autoconfigure.knife4j;

import com.jarvis.framework.autoconfigure.springfox.JarvisSpringfoxProperties;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import springfox.documentation.oas.mappers.ServiceModelToOpenApiMapperImpl;
import springfox.documentation.service.Documentation;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年8月2日
 */
public class JarvisServiceModelToOpenApiMapperImpl extends ServiceModelToOpenApiMapperImpl {

    @Autowired
    private JarvisSpringfoxProperties properties;

    /**
     *
     * @see springfox.documentation.oas.mappers.ServiceModelToOpenApiMapperImpl#mapDocumentation(springfox.documentation.service.Documentation)
     */
    @Override
    public OpenAPI mapDocumentation(Documentation from) {
        final JarvisOpenAPI jarvisOpenAPI = new JarvisOpenAPI();
        final OpenAPI mapDocumentation = super.mapDocumentation(from);
        BeanUtils.copyProperties(mapDocumentation, jarvisOpenAPI);
        jarvisOpenAPI.setBasePath(properties.getServiceName());
        jarvisOpenAPI.setHost(properties.getServiceHost());
        return jarvisOpenAPI;
    }

}
