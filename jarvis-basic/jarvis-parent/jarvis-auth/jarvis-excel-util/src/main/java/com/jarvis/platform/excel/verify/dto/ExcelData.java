package com.jarvis.platform.excel.verify.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @author hewm
 * @date 2022/9/26
 */
@Data
public class ExcelData {


    @ExcelIgnore
    private Long lineNumber;

    @ExcelIgnore
    private Boolean success = true;

    @ExcelProperty(value = "错误信息")
    private String msg;

    public void setMsg(String msg) {
        if (!StringUtils.hasText(this.msg)) {
            this.msg = msg;
        } else {
            this.msg += "," + msg;
        }
    }
}
