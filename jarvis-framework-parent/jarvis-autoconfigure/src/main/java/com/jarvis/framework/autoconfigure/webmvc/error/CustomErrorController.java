package com.jarvis.framework.autoconfigure.webmvc.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jarvis.framework.autoconfigure.security.ArchiveValidateCodeAutoConfiguration;
import com.jarvis.framework.web.rest.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class CustomErrorController extends BasicErrorController {
    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest a) {
        HttpStatus var2;
        if ((var2 = a.getStatus(a)) == HttpStatus.NO_CONTENT) {
            return new ResponseEntity(var2);
        } else {
            Object a;
            if (ObjectUtils.isEmpty(a = a.getErrorAttributes(a, a.getErrorAttributeOptions(a, MediaType.ALL)).get(ArchiveValidateCodeAutoConfiguration.oOoOOo("4T*B8V<")))) {
                a = ArchiveValidateCodeAutoConfiguration.oOoOOo("穒庾冣锨唿");
            }

            HttpServletRequest a = (Map)a.objectMapper.convertValue(RestResponse.response(var2, a), Map.class);
            return new ResponseEntity(a, var2);
        }
    }

    public CustomErrorController(ErrorAttributes a, ErrorProperties a, List<ErrorViewResolver> a) {
        super(a, a, a);
    }
}
