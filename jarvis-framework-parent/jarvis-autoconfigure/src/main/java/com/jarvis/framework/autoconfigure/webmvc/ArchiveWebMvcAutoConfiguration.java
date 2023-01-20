package com.jarvis.framework.autoconfigure.webmvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jarvis.framework.license.LicenseInfo;
import com.jarvis.framework.webmvc.convert.ObjectMapperConverter;
import com.jarvis.framework.webmvc.jackson.customizer.ObjectMapperBuilderCustomizer;
import com.jarvis.framework.webmvc.method.support.CriteriaQueryMethodArgumentResolver;
import com.jarvis.framework.webmvc.util.ApplicationContextUtil;
import com.jarvis.framework.autoconfigure.mybatis.DruidExtendProperties;
import com.jarvis.framework.autoconfigure.security.ArchiveValidateCodeAutoConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.stream.Collectors;

@Configuration(
    proxyBeanMethods = false
)
@AutoConfigureBefore({ErrorMvcAutoConfiguration.class})
@ConditionalOnClass({WebMvcConfigurer.class})
@Import({ppppPP.class})
@ComponentScan(
    basePackages = {"com.gdda.archives.framework.autoconfigure.webmvc", "com.gdda.archives.platform.**.sdk"}
)
public class ArchiveWebMvcAutoConfiguration {
    @Bean
    Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return new ObjectMapperBuilderCustomizer();
    }

    @Bean
    @ConditionalOnMissingBean({ErrorController.class})
    CustomErrorController customErrorController(ErrorAttributes a, ServerProperties a, ObjectProvider<ErrorViewResolver> a) {
        return new CustomErrorController(a, a.getError(), (List)a.orderedStream().collect(Collectors.toList()));
    }

    @Bean(
        name = {"oO0o0O0ooo0o"}
    )
    @ConditionalOnMissingBean(
        name = {"oO0o0O0ooo0o"}
    )
    c errorCidAf(ObjectMapper a) {
        return ppppPP.eSbeiT(a);
    }

    @Bean
    LicenseInfo archiveLicenseInfo(ObjectMapper a) {
        return new LicenseInfo(a);
    }

    @Bean
    ApplicationContextUtil applicationContextHolder() {
        return new ApplicationContextUtil();
    }

    public ArchiveWebMvcAutoConfiguration() {
    }

    @Configuration
    class ArchiveWebMvcConfigurer implements WebMvcConfigurer {
        @Autowired
        private ObjectMapper objectMapper;

        ArchiveWebMvcConfigurer() {
        }

        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> ax) {
            ax.add(new CriteriaQueryMethodArgumentResolver());
        }

        public void addFormatters(FormatterRegistry ax) {
            ax.addConverter(new ObjectMapperConverter(a.objectMapper));
        }

        public void addViewControllers(ViewControllerRegistry ax) {
            ax.addViewController(ArchiveValidateCodeAutoConfiguration.oOoOOo("v")).setViewName(DruidExtendProperties.oOoOOo("=_0T,\u001f<E9]"));
        }
    }
}
