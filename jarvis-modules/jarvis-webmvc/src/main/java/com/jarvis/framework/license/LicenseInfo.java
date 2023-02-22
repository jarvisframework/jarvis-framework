package com.jarvis.framework.license;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jarvis.framework.core.exception.FrameworkException;
import org.springframework.core.io.Resource;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 注册码
 *
 * @author Doug Wang
 * @version 1.0.0 2022年3月29日
 */
public class LicenseInfo {

    private String serviceCode;

    private int status;

    private String enterprise;

    private LocalDateTime registTime;

    private LocalDateTime vaildTime;

    private String mac;

    private final ObjectMapper objectMapper;

    public LicenseInfo(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        try {
            init();
        } catch (final Exception e) {
            throw new FrameworkException("解析注册码[key.license]出错", e);
        }
    }

    @SuppressWarnings("unchecked")
    private void init() throws Exception {
        final Resource resource = LicenseUtil.getResource();
        if (null == resource || !resource.exists()) {
            status = 0;
            return;
        }

        final String str = LicenseUtil.readLicenseCode();
        if (null == str) {
            status = 0;
            return;
        }

        final Map<String, Object> bean = objectMapper.readValue(str, Map.class);
        this.status = Integer.valueOf(String.valueOf(bean.get("status"))).intValue();
        this.enterprise = (String) bean.get("enterprise");
        this.serviceCode = (String) bean.get("serviceCode");
        this.registTime = LocalDateTime.parse((String) bean.get("registTime"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.mac = ((String) bean.get("mac")).toUpperCase();
        if (!ObjectUtils.isEmpty(bean.get("vaildTime"))) {
            this.vaildTime = LocalDateTime.parse((String) bean.get("vaildTime"),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
    }

    public List<String> getServiceCodes() {
        if (null == serviceCode) {
            return null;
        }
        return Stream.of(serviceCode.split(",")).collect(Collectors.toList());
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @return the enterprise
     */
    public String getEnterprise() {
        return enterprise;
    }

    /**
     * @return the registTime
     */
    public LocalDateTime getRegistTime() {
        return registTime;
    }

    /**
     * @return the vaildTime
     */
    public LocalDateTime getVaildTime() {
        return vaildTime;
    }

    /**
     * @return the mac
     */
    public String getMac() {
        return mac;
    }

}
