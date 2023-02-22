package com.jarvis.framework.autoconfigure.webmvc.error;

import com.fasterxml.jackson.databind.ObjectMapper;
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

/**
 * 处理异常并返回结构一致的 JSON
 *
 * @author Doug Wang
 * @version 1.0.0 2021年5月7日
 */
public class CustomErrorController extends BasicErrorController {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * @param errorAttributes
     * @param errorProperties
     */
    public CustomErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties,
                                 List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorProperties, errorViewResolvers);
    }

    /**
     * @see org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController#error(
     *      javax.servlet.http.HttpServletRequest)
     */
    @SuppressWarnings("unchecked")
    @Override
    @RequestMapping
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        final HttpStatus status = getStatus(request);
        if (status == HttpStatus.NO_CONTENT) {
            return new ResponseEntity<>(status);
        }
        final Map<String, Object> body = getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.ALL));
        Object message = body.get("message");
        if (ObjectUtils.isEmpty(message)) {
            message = "程序出错啦";
        }
        final Map<String,
                Object> response = objectMapper.convertValue(RestResponse.response(status, message), Map.class);
        return new ResponseEntity<>(response, status);
    }

}
