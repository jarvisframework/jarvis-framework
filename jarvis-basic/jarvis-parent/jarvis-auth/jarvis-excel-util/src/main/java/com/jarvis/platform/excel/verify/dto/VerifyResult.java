package com.jarvis.platform.excel.verify.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author hewm
 * @date 2022/9/26
 */
@Data
@Accessors(chain = true)
public class VerifyResult<T> {
    @ApiModelProperty("成功导入")
    private List<T> success;

    @ApiModelProperty("失败导入")
    private List<T> fail;


}
