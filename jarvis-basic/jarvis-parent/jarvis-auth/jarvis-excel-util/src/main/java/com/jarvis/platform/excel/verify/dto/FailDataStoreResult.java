package com.jarvis.platform.excel.verify.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hewm
 * @date 2022/9/26
 */
@Getter
@Setter
public class FailDataStoreResult<T> extends VerifyResult<T> {
    @ApiModelProperty("下载文件根目录: archive-type")
    private String location;

    @ApiModelProperty("下载文件子路径: import/xxx.txt")
    private String path;
}
