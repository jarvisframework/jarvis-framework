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
            this.init();
        } catch (Exception var3) {
            throw new FrameworkException("解析注册码[key.license]出错", var3);
        }
    }

    private void init() throws Exception {
        Resource resource = LicenseUtil.getResource();
        if (null != resource && resource.exists()) {
            String str = LicenseUtil.readLicenseCode();
            if (null == str) {
                this.status = 0;
            } else {
                Map<String, Object> bean = (Map)this.objectMapper.readValue(str, Map.class);
                this.status = Integer.valueOf(String.valueOf(bean.get("status")));
                this.enterprise = (String)bean.get("enterprise");
                this.serviceCode = (String)bean.get("serviceCode");
                this.registTime = LocalDateTime.parse((String)bean.get("registTime"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                this.mac = ((String)bean.get("mac")).toUpperCase();
                if (!ObjectUtils.isEmpty(bean.get("vaildTime"))) {
                    this.vaildTime = LocalDateTime.parse((String)bean.get("vaildTime"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                }

            }
        } else {
            this.status = 0;
        }
    }

    public List<String> getServiceCodes() {
        return null == this.serviceCode ? null : (List)Stream.of(this.serviceCode.split(",")).collect(Collectors.toList());
    }

    public int getStatus() {
        return this.status;
    }

    public String getEnterprise() {
        return this.enterprise;
    }

    public LocalDateTime getRegistTime() {
        return this.registTime;
    }

    public LocalDateTime getVaildTime() {
        return this.vaildTime;
    }

    public String getMac() {
        return this.mac;
    }
}
